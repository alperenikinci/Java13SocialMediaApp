package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNewPostRequestDto {

    private String token; //authId -> Once login olmak zorundayiz.
    private String title;
    private String content;
    private List<String> mediaUrls;
}
