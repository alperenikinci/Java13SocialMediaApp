package com.bilgeadam.manager;

import com.bilgeadam.dto.request.CreateUserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "http://localhost:7071/api/v1/user-profile",name = "auth-userprofile")
public interface UserManager {

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequestDto dto);
}
