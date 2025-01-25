package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Service
public class SearchService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	public Map<String, Object> search(String keyword) {
		List<User> users = userRepository.searchUsers(keyword);
		List<Task> tasks = taskRepository.searchTasks(keyword);
		List<Comment> comments = commentRepository.searchComments(keyword);
		
		Map<String, Object> results = new HashMap<>();
		results.put("users", users);
		results.put("tasks", tasks);
		results.put("comments", comments);
		
		return results;
	}

}
