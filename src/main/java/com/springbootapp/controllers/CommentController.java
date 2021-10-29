package com.springbootapp.controllers;

import com.springbootapp.models.Comment;
import com.springbootapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public Comment postComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @GetMapping("/comments")
    public Iterable<Comment> getComment() { return commentService.findAllComments(); }
}
