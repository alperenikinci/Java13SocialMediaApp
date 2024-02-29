package com.bilgeadam.entity;

import com.bilgeadam.utility.enums.EStatus;
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
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class Post extends BaseEntity {

    @Id
    private String id;
    private String userId;
    private String username;
    private String userAvatar;
    private String title;
    private String content;
    private List<String> mediaUrls;
    private List<String> likes;
    private List<String> comments;
}
