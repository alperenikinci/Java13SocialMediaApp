package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateUserRequestDto;
import com.bilgeadam.entity.UserProfile;
import com.bilgeadam.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.bilgeadam.constant.RestApiUrls.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(USER_PROFILE) //http://localhost:7071/api/v1/user-profile
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequestDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }
}
