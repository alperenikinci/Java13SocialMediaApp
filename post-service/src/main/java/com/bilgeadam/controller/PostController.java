package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateNewPostRequestDto;
import com.bilgeadam.dto.request.PostCommentRequestDto;
import com.bilgeadam.dto.request.PostLikeRequestDto;
import com.bilgeadam.entity.Post;
import com.bilgeadam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.bilgeadam.constant.RestApiUrls.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(POST)
public class PostController {

    private final PostService postService;

    @PostMapping(CREATE)
    public ResponseEntity<Post> createPost(@RequestBody CreateNewPostRequestDto dto){
        return ResponseEntity.ok(postService.createPost(dto));
    }

    @PostMapping(COMMENT_A_POST)
    public ResponseEntity<Post> commentPost(@RequestBody PostCommentRequestDto dto){
        return ResponseEntity.ok(postService.commentAPost(dto));
    }
    @PostMapping(LIKE_A_POST)
    public ResponseEntity<Post> likePost(@RequestBody PostLikeRequestDto dto){
        return ResponseEntity.ok(postService.likeAPost(dto));
    }
}
