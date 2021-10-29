package com.springbootapp.services;

import com.springbootapp.models.Comment;
import com.springbootapp.models.Model;
import com.springbootapp.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public Comment createComment(Comment comment) {
        if (comment.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Comments cannot be empty!");
        }
        return commentRepo.save(comment);
    }

    public Iterable<Comment> findAllComments() {
        return commentRepo.findAll();
    }
}
