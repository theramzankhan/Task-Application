package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Comment;
import com.example.demo.entity.CommentReaction;
import com.example.demo.entity.User;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
	
	Optional<CommentReaction> findByCommentAndUser(Comment comment, User user);

}
