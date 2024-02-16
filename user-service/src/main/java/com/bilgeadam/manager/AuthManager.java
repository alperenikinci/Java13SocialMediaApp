package com.bilgeadam.manager;

import com.bilgeadam.dto.request.UpdateEmailRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@FeignClient(url = "http://localhost:7070/api/v1/auth",name = "userprofile-auth")
public interface AuthManager {

    @PutMapping("/update-email")
    public ResponseEntity<Boolean> updateEmail(@RequestBody UpdateEmailRequestDto dto);


}
