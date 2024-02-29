package com.bilgeadam.manager;


import com.bilgeadam.dto.response.UserProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.bilgeadam.constant.RestApiUrls.FIND_BY_TOKEN;


@FeignClient(url = "http://localhost:7071/api/v1/user-profile",name = "post-userprofile")
public interface UserManager {


    @GetMapping(FIND_BY_TOKEN)
    public ResponseEntity<UserProfileResponseDto> findUserByToken(@RequestParam String token);
}

