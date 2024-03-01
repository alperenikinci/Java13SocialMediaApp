package com.bilgeadam.service;

import com.bilgeadam.dto.request.CommentLikeRequestDto;
import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.entity.Comment;
import com.bilgeadam.entity.Like;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.PostManagerException;
import com.bilgeadam.manager.UserManager;
import com.bilgeadam.repository.CommentRepository;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService extends ServiceManager<Comment,String> {

    private final CommentRepository commentRepository;
    private final UserManager userManager;
    private final LikeService likeService;

    public CommentService(CommentRepository commentRepository, UserManager userManager, LikeService likeService) {
        super(commentRepository);
        this.commentRepository=commentRepository;
        this.userManager = userManager;
        this.likeService = likeService;
    }

    public Comment likeAComment(CommentLikeRequestDto dto) {
        Comment comment = getComment(dto.getCommentId());
        UserProfileResponseDto userProfile = getUserProfile(dto.getToken());

        Like like = buildLike(userProfile, comment);
        if(likeService.existsByUserIdAndCommentId(userProfile.getId(), comment.getId())){
            throw new PostManagerException(ErrorType.LIKE_ALREADY_EXISTS);
        }
        likeService.save(like);
        comment.getCommentLikes().add(like.getCommentId());

        return update(comment);
    }

    private Comment getComment(String commentId) {
        return findById(commentId).orElseThrow(() -> new PostManagerException(ErrorType.COMMENT_NOT_FOUND));
    }

    private Like buildLike(UserProfileResponseDto userProfile, Comment comment) {
        return Like.builder()
                .postId(comment.getPostId())
                .userId(userProfile.getId())
                .username(userProfile.getUsername())
                .commentId(comment.getId())
                .userAvatar(userProfile.getAvatar())
                .build();
    }

    private UserProfileResponseDto getUserProfile(String token) {
        return Optional.ofNullable(userManager.findUserByToken(token).getBody())
                .orElseThrow(() -> new PostManagerException(ErrorType.USER_NOT_FOUND));
    }


}
