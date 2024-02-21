package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateUserRequestDto;
import com.bilgeadam.dto.request.UpdateEmailRequestDto;
import com.bilgeadam.dto.request.UserProfileUpdateRequestDto;
import com.bilgeadam.entity.UserProfile;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.manager.AuthManager;
import com.bilgeadam.mapper.UserProfileMapper;
import com.bilgeadam.repository.UserProfileRepository;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import com.bilgeadam.utility.enums.EStatus;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {

    private final UserProfileRepository userProfileRepository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthManager authManager;
    private final CacheManager cacheManager;

    public UserProfileService(UserProfileRepository userProfileRepository, JwtTokenManager jwtTokenManager, AuthManager authManager, CacheManager cacheManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
        this.cacheManager = cacheManager;
    }

    public Boolean createUser(CreateUserRequestDto dto) {
        try {
            save(UserProfileMapper.INSTANCE.fromCreateRequestToUserProfile(dto));
            return true;
        } catch (Exception e) {
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    public Boolean activateStatus(Long authId) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(authId);
        if(optionalUserProfile.isPresent()){
            optionalUserProfile.get().setStatus(EStatus.ACTIVE);
            update(optionalUserProfile.get());
            return true;
        } else {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
    }

    public Boolean update(UserProfileUpdateRequestDto dto){
        Optional<Long> authId = jwtTokenManager.getIdFromToken(dto.getToken());
        if(authId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(authId.get());
        if (optionalUserProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
//        UserProfile userProfile = UserProfileMapper.INSTANCE.fromUpdateRequestToUserProfile(dto);
//        userProfile.setId(optionalUserProfile.get().getId());
//        userProfile.setAuthId(optionalUserProfile.get().getAuthId());
//        userProfile.setUsername(optionalUserProfile.get().getUsername());

        UserProfile userProfile = optionalUserProfile.get();
        userProfile.setEmail(dto.getEmail());
        userProfile.setPhone(dto.getPhone());
        userProfile.setAvatar(dto.getAvatar());
        userProfile.setAddress(dto.getAddress());
        userProfile.setAbout(dto.getAbout());
        update(userProfile);
        cacheManager.getCache("findByUserName").evict(userProfile.getUsername().toLowerCase());
        cacheManager.getCache("findByRole").clear();


        authManager.updateEmail(UpdateEmailRequestDto.builder()
                .id(userProfile.getAuthId())
                .email(userProfile.getEmail())
                .build());
        return true;
    }

    public Boolean softDeleteByToken(String token){
        Optional<Long> optionalAuthId = jwtTokenManager.getIdFromToken(token);
        if(optionalAuthId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(optionalAuthId.get());
        if(optionalUserProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        optionalUserProfile.get().setStatus(EStatus.DELETED);
        update(optionalUserProfile.get());
        return true;
    }

    @Cacheable(value = "findByUserName",key = "#username.toLowerCase()")
    public UserProfile findByUsername(String username) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUsernameIgnoreCase(username);
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        return userProfile.get();
    }

    @Cacheable(value="findByRole",key = "#role.toUpperCase()") //USER  //findbyrole::USER
    public List<UserProfile> findByRole(String role){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        ResponseEntity<List<Long>> authIds= authManager.findByRole(role);
        List<Long> authIds = authManager.findByRole(role).getBody();

        return authIds.stream().map(x->  userProfileRepository.findByAuthId(x)
                .orElseThrow( () -> {throw new UserManagerException(ErrorType.USER_NOT_FOUND);})).collect(Collectors.toList());
    }
}
