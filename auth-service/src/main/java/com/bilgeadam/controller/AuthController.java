package com.bilgeadam.controller;

import com.bilgeadam.dto.request.ActivateStatusRequestDto;
import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UpdateEmailRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.service.AuthService;
//import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.enums.ERole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.RestApiUrls.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;
    private final JwtTokenManager tokenManager;
    private final CacheManager cacheManager;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register (@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping(REGISTER_WITH_RABBITMQ)
    public ResponseEntity<RegisterResponseDto>  registerWithRabbitMq (@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.registerWithRabbitMQ(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(ACTIVATE_STATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateStatusRequestDto dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @PutMapping(UPDATE_EMAIL)
    public ResponseEntity<Boolean> updateEmail(@RequestBody UpdateEmailRequestDto dto){
        return ResponseEntity.ok(authService.updateEmail(dto));
    }

    @DeleteMapping(DELETE_BY_TOKEN)
    public ResponseEntity<Boolean> deleteByToken(@RequestParam String token){
        return ResponseEntity.ok(authService.softDeleteByToken(token));
    }

    @GetMapping("/create-token")
    public ResponseEntity<String> createToken(Long id, ERole role){
        return ResponseEntity.ok(tokenManager.createToken(id,role).get());
    }
    @GetMapping("/create-token2")
    public ResponseEntity<String> createToken(Long id){
        return ResponseEntity.ok(tokenManager.createToken(id).get());
    }

    @GetMapping("/get-id-from-token")
    public ResponseEntity<Long> getIdFromToken(String token){
        return ResponseEntity.ok(tokenManager.getIdFromToken(token).get());
    }

    @GetMapping("/get-role-from-token")
    public ResponseEntity<String> getRoleFromToken(String token){
        return ResponseEntity.ok(tokenManager.getRoleFromToken(token).get());
    }

    @GetMapping("/redis")
    @Cacheable(value = "redis-example",key ="#value.toUpperCase()")
    public String redisExample(String value){
        try {
            Thread.sleep(2000);
            return value;
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/redis-delete")
//    @CacheEvict(cacheNames = "redis-example",allEntries = true )
    public void redisDelete(){
        try {
            cacheManager.getCache("redis-example").clear();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/redis-delete2")
    public Boolean redisDelete2(String value){
        try {
            cacheManager.getCache("redis-example").evict(value);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @GetMapping(FIND_BY_ROLE)
    public ResponseEntity<List<Long>> findByRole(@RequestParam String role){
        return ResponseEntity.ok(authService.findByRole(role));
    }

}
