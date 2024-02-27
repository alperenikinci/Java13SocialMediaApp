package com.bilgeadam.controller;

import com.bilgeadam.domain.UserProfile;
import com.bilgeadam.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.bilgeadam.constant.RestApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_PROFILE)
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping(FIND_ALL)
    public ResponseEntity<Iterable<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }
}
