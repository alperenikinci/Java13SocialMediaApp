package com.bilgeadam.service;

import com.bilgeadam.entity.Comment;
import com.bilgeadam.repository.CommentRepository;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ServiceManager<Comment,String> {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        super(commentRepository);
        this.commentRepository=commentRepository;
    }
}
