package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateUserRequestDto;
import com.bilgeadam.entity.UserProfile;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.mapper.UserProfileMapper;
import com.bilgeadam.repository.UserProfileRepository;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
    }

    public Boolean createUser(CreateUserRequestDto dto) {
        try {
            save(UserProfileMapper.INSTANCE.fromCreateRequestToUserProfile(dto));
            return true;
        } catch (Exception e) {
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }

    }
}
