package com.bilgeadam.entity;

import com.bilgeadam.dto.response.UserProfileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Like extends BaseEntity{
    @Id
    private String id;
    private String userId;
    private String postId;
    private String commentId;
    private String username;
    private String userAvatar;



//    public static Like buildLike(UserProfileResponseDto userProfile, Post post) {
//        return Like.builder()
//                .postId(post.getId())
//                .userId(userProfile.getId())
//                .username(userProfile.getUsername())
//                .userAvatar(userProfile.getAvatar())
//                .build();
//    }
}