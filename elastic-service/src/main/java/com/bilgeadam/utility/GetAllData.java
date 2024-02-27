package com.bilgeadam.utility;

import com.bilgeadam.domain.UserProfile;
import com.bilgeadam.manager.UserManager;
import com.bilgeadam.service.UserProfileService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GetAllData {

    private final UserProfileService userProfileService;
    private final UserManager userManager;
//    @PostConstruct
    public void initData(){
        List<UserProfile> userProfileList = userManager.findAll().getBody();
        userProfileService.saveAll(Objects.requireNonNull(userProfileList));
    }
}
