package com.bilgeadam.manager;

import com.bilgeadam.domain.UserProfile;
import com.bilgeadam.dto.request.CreateUserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.RestApiUrls.*;


@FeignClient(url = "http://localhost:7071/api/v1/user-profile",name = "elastic-userprofile")
public interface UserManager {

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<UserProfile>> findAll();
}

