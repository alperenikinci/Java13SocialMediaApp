package com.bilgeadam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Comment extends BaseEntity {
    @Id
    private String id;
    private String userId;
    private String postId;
    private String username;
    private String userAvatar;
    private String content;
//    private List<String> subCommentId; //yorumların yorumu için kullanabiliriz.

    @Builder.Default
    private List<String> commentLikes= new ArrayList<>();
}
