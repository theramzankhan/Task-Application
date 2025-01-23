package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Comment addComment(Long taskId, Integer userId, String message) {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		Comment comment = new Comment();
		comment.setMessage(message);
		comment.setTask(task);
		comment.setUser(user);
		comment.setCreatedAt(LocalDateTime.now());
		
		return commentRepository.save(comment);
	}
	
	public List<Comment> getCommentByTask(Long taskId) {
		return commentRepository.findByTaskId(taskId);
	}
	
	
	public Comment likeComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
		comment.setLikes(comment.getLikes() + 1); //increment likes counter
		return commentRepository.save(comment);
	}
	
	public Comment dislikeComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
		comment.setDislikes(comment.getDislikes() + 1);
		return commentRepository.save(comment);
	}

}
