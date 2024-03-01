package com.bilgeadam.repository;

import com.bilgeadam.entity.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends MongoRepository<Like,String> {

    Boolean existsByUserIdAndPostId(String userId, String postId);
    Boolean existsByUserIdAndCommentId(String userId, String commentId);

}
