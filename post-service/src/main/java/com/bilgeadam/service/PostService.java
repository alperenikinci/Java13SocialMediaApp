package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateNewPostRequestDto;
import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.entity.Post;
import com.bilgeadam.manager.UserManager;
import com.bilgeadam.mapper.PostMapper;
import com.bilgeadam.repository.PostRepository;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class PostService extends ServiceManager<Post,String> {

    private final PostRepository postRepository;
    private final UserManager userManager;

    public PostService(PostRepository postRepository, UserManager userManager) {
        super(postRepository);
        this.postRepository=postRepository;
        this.userManager = userManager;
    }

    public Post createPost(CreateNewPostRequestDto dto) {
        Post post = PostMapper.INSTANCE.fromCreateRequestToPost(dto);
        UserProfileResponseDto userProfileResponseDto = userManager.findUserByToken(dto.getToken()).getBody();

        post.setUserId(userProfileResponseDto.getId());
        post.setUsername(userProfileResponseDto.getUsername());
        post.setUserAvatar(userProfileResponseDto.getAvatar());
        return save(post);
    }
}
