package com.bilgeadam.repository;

import com.bilgeadam.entity.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFollowRepository extends MongoRepository<Follow,String> {

    Optional<Follow> findByFollowingUsersIdAndFollowedUsersId(String followingUserId,String followedUserID);

}