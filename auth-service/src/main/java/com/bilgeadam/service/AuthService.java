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

import com.bilgeadam.utility.enums.EStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final AuthRepository authRepository;
    private final UserManager userManager;
    private final JwtTokenManager jwtTokenManager;

    public AuthService(AuthRepository authRepository, UserManager userManager, JwtTokenManager jwtTokenManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.jwtTokenManager = jwtTokenManager;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = AuthMapper.INSTANCE.fromRegisterRequestToAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
        save(auth);
        userManager.createUser(AuthMapper.INSTANCE.fromAuthToCreateUserRequestDto(auth));

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
}
