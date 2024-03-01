package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CommentLikeRequestDto;
import com.bilgeadam.entity.Comment;
import com.bilgeadam.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bilgeadam.constant.RestApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMMENT)
public class CommentController {

    private final CommentService commentService;

    @PostMapping(LIKE_A_COMMENT)
    public ResponseEntity<Comment> likePost(@RequestBody CommentLikeRequestDto dto){
        return ResponseEntity.ok(commentService.likeAComment(dto));
    }
}
