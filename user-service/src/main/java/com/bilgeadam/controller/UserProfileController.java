package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateUserRequestDto;
import com.bilgeadam.dto.request.UserProfileUpdateRequestDto;
import com.bilgeadam.entity.UserProfile;
import com.bilgeadam.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.RestApiUrls.*;
import static com.bilgeadam.constant.RestApiUrls.ACTIVATE_STATUS;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_PROFILE) //http://localhost:7071/api/v1/user-profile
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequestDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }

    @GetMapping(ACTIVATE_STATUS+"/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.activateStatus(authId));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> update(@RequestBody UserProfileUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.update(dto));
    }

    @DeleteMapping(DELETE_BY_TOKEN)
    public ResponseEntity<Boolean> deleteByToken(@RequestParam String token){
        return ResponseEntity.ok(userProfileService.softDeleteByToken(token));
    }

    @GetMapping("/find-by-username")
    public ResponseEntity<UserProfile> findByUsername(@RequestParam String username){
        return ResponseEntity.ok(userProfileService.findByUsername(username));
    }

    @GetMapping(FIND_BY_ROLE)
    public ResponseEntity<List<UserProfile>> findByRole(@RequestParam String role){
        return ResponseEntity.ok(userProfileService.findByRole(role));
    }

}
