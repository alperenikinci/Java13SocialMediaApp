package com.bilgeadam.manager;

import com.bilgeadam.dto.request.CreateUserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constant.RestApiUrls.ACTIVATE_STATUS;
import static com.bilgeadam.constant.RestApiUrls.DELETE_BY_ID;


@FeignClient(url = "http://localhost:7071/api/v1/user-profile",name = "auth-userprofile")
public interface UserManager {

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequestDto dto);
    @GetMapping(ACTIVATE_STATUS+"/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId);
    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id);
    //@RequestParam Controller sınıfında default deger olarak atansa da benim UserManager'ım bir controller degil ve bunu bilemez.
    //dolayisiyla bir veri gonderirken mutlaka @RequestParam @RequestBody @PathVariable gibi anotasyonlardan birisini mutlaka kullanmak zorundayim.
}
