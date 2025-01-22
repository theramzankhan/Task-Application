package com.example.demo.UserController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Comment;
import com.example.demo.entity.CommentRequest;
import com.example.demo.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
//	@PostMapping("/task/{taskId}")
//	public ResponseEntity<Comment> addComment(
//			@PathVariable Long taskId,
//			@RequestParam Integer userId,
//			@RequestParam String message
//			) {
//		Comment comment = commentService.addComment(taskId, userId, message);
//		return ResponseEntity.ok(comment);
//	}
	
	@PostMapping("/task/{taskId}")
	public ResponseEntity<Comment> addComment(
			@PathVariable Long taskId,
			@Valid @RequestBody CommentRequest commentRequest
			) {
		Comment comment = commentService.addComment(taskId, commentRequest.getUserId(), commentRequest.getMessage());
		return ResponseEntity.ok(comment);
	}
	
	@GetMapping("/task/{taskId}")
	public ResponseEntity<List<Comment>> getCommentByTask(@PathVariable Long taskId) {
		List<Comment> comments = commentService.getCommentByTask(taskId);
		return ResponseEntity.ok(comments);
	}

}
