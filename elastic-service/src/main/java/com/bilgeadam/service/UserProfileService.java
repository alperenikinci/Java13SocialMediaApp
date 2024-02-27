package com.bilgeadam.service;


import com.bilgeadam.domain.UserProfile;
import com.bilgeadam.mapper.ElasticMapper;
import com.bilgeadam.rabbitmq.model.RegisterElasticModel;
import com.bilgeadam.repository.UserProfileRepository;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile createUserWithRabbitMq(RegisterElasticModel model) {
        return save(ElasticMapper.INSTANCE.fromRegisterElasticModelToUserProfile(model));
    }
}
