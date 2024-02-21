package com.bilgeadam.service;

import com.bilgeadam.dto.request.ActivateStatusRequestDto;
import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UpdateEmailRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.entity.Auth;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.UserManager;
import com.bilgeadam.mapper.AuthMapper;
import com.bilgeadam.repository.AuthRepository;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;

import com.bilgeadam.utility.enums.ERole;
import com.bilgeadam.utility.enums.EStatus;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final AuthRepository authRepository;
    private final UserManager userManager;
    private final JwtTokenManager jwtTokenManager;
    private final CacheManager cacheManager;

    public AuthService(AuthRepository authRepository, UserManager userManager, JwtTokenManager jwtTokenManager, CacheManager cacheManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.jwtTokenManager = jwtTokenManager;
        this.cacheManager = cacheManager;
    }

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = AuthMapper.INSTANCE.fromRegisterRequestToAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
        save(auth);
//        try {
        userManager.createUser(AuthMapper.INSTANCE.fromAuthToCreateUserRequestDto(auth));
        cacheManager.getCache("findByRole").evict(auth.getRole().toString().toUpperCase());
//        } catch (Exception e){
//            e.printStackTrace();
//            delete(auth);
//        }
        return AuthMapper.INSTANCE.fromAuthToRegisterResponseDto(auth);
    }

    public String login(LoginRequestDto dto) {
        Optional<Auth> authOptional = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(),dto.getPassword());
        if(authOptional.isEmpty()){
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        }
        if(authOptional.get().getStatus().equals(EStatus.ACTIVE)){
            return jwtTokenManager.createToken(authOptional.get().getId(),authOptional.get().getRole())
                    .orElseThrow(() -> {throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);});
        } else {
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
    }

    public Boolean activateStatus(ActivateStatusRequestDto dto) {
        Optional<Auth> optionalAuth = findById(dto.getAuthId());
        if(optionalAuth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if(optionalAuth.get().getActivationCode().equals(dto.getActivationCode())){
            optionalAuth.get().setStatus(EStatus.ACTIVE);
            update(optionalAuth.get());
            userManager.activateStatus(optionalAuth.get().getId());
            return true;
        } else {
            throw new AuthManagerException(ErrorType.ACTIVATION_CODE_ERROR);
        }
    }

    public Boolean updateEmail(UpdateEmailRequestDto dto) {
        Optional<Auth> authOptional = authRepository.findById(dto.getId());
        if(authOptional.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        authOptional.get().setEmail(dto.getEmail());
        update(authOptional.get());
        return true;
    }

    public Boolean softDeleteByToken(String token){
        Optional<Long> optionalId = jwtTokenManager.getIdFromToken(token);
        if(optionalId.isEmpty()){
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<Auth> authOptional = authRepository.findById(optionalId.get());
        if(authOptional.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        authOptional.get().setStatus(EStatus.DELETED);
        update(authOptional.get());
        userManager.deleteByToken(token);
        return true;
    }

    public List<Long> findByRole(String role) {
        ERole myRole;
        try {
            myRole = ERole.valueOf(role.toUpperCase(Locale.ENGLISH)); //admin -> ADMİN -> ADMIN
        } catch (Exception e){
            throw new AuthManagerException(ErrorType.ROLE_NOT_FOUND);
        }
        return authRepository.findAllByRole(myRole).stream().map(x->x.getId()).collect(Collectors.toList());
    }
}
