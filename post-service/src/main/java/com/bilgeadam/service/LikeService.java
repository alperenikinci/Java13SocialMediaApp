package com.bilgeadam.service;

import com.bilgeadam.entity.Like;
import com.bilgeadam.repository.LikeRepository;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class LikeService extends ServiceManager<Like,String> {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        super(likeRepository);
        this.likeRepository=likeRepository;
    }
}
