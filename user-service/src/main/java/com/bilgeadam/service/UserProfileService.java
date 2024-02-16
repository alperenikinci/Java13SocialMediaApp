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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {

    private final UserProfileRepository userProfileRepository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthManager authManager;

    public UserProfileService(UserProfileRepository userProfileRepository, JwtTokenManager jwtTokenManager, AuthManager authManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
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

        authManager.updateEmail(UpdateEmailRequestDto.builder()
                .id(userProfile.getAuthId())
                .email(userProfile.getEmail())
                .build());
        return true;
    }

    public Boolean softDeleteById(Long id){
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByAuthId(id);
        if(optionalUserProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        optionalUserProfile.get().setStatus(EStatus.DELETED);
        update(optionalUserProfile.get());
        return true;
    }
}
