package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateNewPostRequestDto;
import com.bilgeadam.dto.request.PostCommentRequestDto;
import com.bilgeadam.dto.request.PostLikeRequestDto;
import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.entity.Comment;
import com.bilgeadam.entity.Like;
import com.bilgeadam.entity.Post;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.PostManagerException;
import com.bilgeadam.manager.UserManager;
import com.bilgeadam.mapper.PostMapper;
import com.bilgeadam.repository.PostRepository;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService extends ServiceManager<Post,String> {

    private final PostRepository postRepository;
    private final UserManager userManager;
    private final LikeService likeService;
    private final CommentService commentService;

    public PostService(PostRepository postRepository, UserManager userManager, LikeService likeService, CommentService commentService) {
        super(postRepository);
        this.postRepository = postRepository;
        this.userManager = userManager;
        this.likeService = likeService;
        this.commentService = commentService;
    }

    public Post createPost(CreateNewPostRequestDto dto) {
        Post post = PostMapper.INSTANCE.fromCreateRequestToPost(dto);
        UserProfileResponseDto userProfileResponseDto = userManager.findUserByToken(dto.getToken()).getBody();

        post.setUserId(userProfileResponseDto.getId());
        post.setUsername(userProfileResponseDto.getUsername());
        post.setUserAvatar(userProfileResponseDto.getAvatar());
        return save(post);
    }

//    public Post likeAPost2(PostLikeRequestDto dto){
//       Optional<UserProfileResponseDto> userProfileResponseDto = Optional.ofNullable(userManager.findUserByToken(dto.getToken()).getBody());
//       if(userProfileResponseDto.isPresent()){
//           Optional<Post> post = findById(dto.getPostId());
//           if(post.isPresent()){
//               Like like = Like.builder()
//                       .postId(post.get().getId())
//                       .userId(userProfileResponseDto.get().getId())
//                       .username(userProfileResponseDto.get().getUsername())
//                       .userAvatar(userProfileResponseDto.get().getAvatar())
//                       .build();
//               likeService.save(like);
//               post.get().getLikes().add(like.getId());
//               return update(post.get());
//           } else {
//               throw new PostManagerException(ErrorType.POST_NOT_FOUND);
//           }
//       } else {
//           throw new PostManagerException(ErrorType.USER_NOT_FOUND);
//       }
//    }


    public Post likeAPost(PostLikeRequestDto dto) {
        UserProfileResponseDto userProfile = getUserProfile(dto.getToken());
        Post post = getPost(dto.getPostId());

//        Like like = Like.buildLike(userProfile,post);
        Like like = buildLike(userProfile,post);
        if (likeService.existsByUserIdAndPostId(userProfile.getId(), post.getId())) {
            throw new PostManagerException(ErrorType.LIKE_ALREADY_EXISTS);
        }
        likeService.save(like);
        post.getLikes().add(like.getId());

        return update(post);
    }

    public Post commentAPost(PostCommentRequestDto dto) {
        UserProfileResponseDto userProfile = getUserProfile(dto.getToken());
        Post post = getPost(dto.getPostId());


        Comment comment = buildComment(userProfile, dto.getContent(), post);
        commentService.save(comment);
        post.getComments().add(comment.getId());
        return update(post);
    }

    private Like buildLike(UserProfileResponseDto userProfile, Post post) {
        return Like.builder()
                .postId(post.getId())
                .userId(userProfile.getId())
                .username(userProfile.getUsername())
                .userAvatar(userProfile.getAvatar())
                .build();
    }

    private Comment buildComment(UserProfileResponseDto userProfile, String content, Post post) {
        return Comment.builder()
                .userId(userProfile.getId())
                .postId(post.getId())
                .username(userProfile.getUsername())
                .userAvatar(userProfile.getAvatar())
                .content(content)
                .build();
    }

    private UserProfileResponseDto getUserProfile(String token) {
        return Optional.ofNullable(userManager.findUserByToken(token).getBody())
                .orElseThrow(() -> new PostManagerException(ErrorType.USER_NOT_FOUND));
    }

    private Post getPost(String postId) {
        return findById(postId).orElseThrow(() -> new PostManagerException(ErrorType.POST_NOT_FOUND));
    }

}
