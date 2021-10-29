package com.springbootapp.repos;


import com.springbootapp.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepo extends CrudRepository<Comment, Integer> {}
