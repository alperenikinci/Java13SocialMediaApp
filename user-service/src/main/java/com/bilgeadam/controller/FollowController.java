package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateFollowRequestDto;
import com.bilgeadam.entity.Follow;
import com.bilgeadam.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import  static  com.bilgeadam.constant.RestApiUrls.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(FOLLOW)
public class FollowController {

    private final FollowService followService;

    @PostMapping(CREATE)
    public ResponseEntity<?> createFollow(@RequestBody CreateFollowRequestDto dto){
        return ResponseEntity.ok(followService.createFollow(dto));
    }

    @GetMapping(FIND_ALL)
    public  ResponseEntity<List<Follow>> findAll(){
        return  ResponseEntity.ok(followService.findAll());
    }
}