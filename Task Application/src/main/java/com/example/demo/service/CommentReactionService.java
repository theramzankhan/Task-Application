package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.CommentReaction;
import com.example.demo.entity.User;
import com.example.demo.repository.CommentReactionRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;

@Service
public class CommentReactionService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private CommentReactionRepository commentReactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void reactToComment(Long commendId, Integer userId, boolean liked) {
		Comment comment = commentRepository.findById(commendId).orElseThrow(() -> new RuntimeException("Comment not found"));
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		CommentReaction reaction = commentReactionRepository.findByCommentAndUser(comment, user).orElse(new CommentReaction());
		reaction.setComment(comment);
		reaction.setUser(user);
		reaction.setLiked(liked);
		
		commentReactionRepository.save(reaction);
	}

}
