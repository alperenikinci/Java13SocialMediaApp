package com.bilgeadam.entity;

import com.bilgeadam.utility.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class UserProfile extends BaseEntity {

    @Id
    private String id;

    private Long authId;
    private String username;
    private String email;
    private String phone;
    private String avatar;
    private String address;
    private String about;

    @Builder.Default
    private EStatus status = EStatus.PENDING;

    @Builder.Default
    private List<String> follows=new ArrayList<>();
    @Builder.Default
    private List<String> followers=new ArrayList<>();
}
