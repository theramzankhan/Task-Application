package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.CommentDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskDto;
import com.example.demo.entity.TaskPriority;
import com.example.demo.entity.TaskStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
			
	public Optional<Task> getTasksByUserId(Long userId) {
        return taskRepository.findById(userId);
    }

    public Task createTaskForUser(Integer userId, Task task) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        task.setUser(user);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
    
    public List<Task> getTaskBySatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
    
    public Task markTaskAsCompleted(Long taskId) {
    	Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found with id " + taskId));
    	
    	//Set the status to COMPLETED
    	task.setStatus(TaskStatus.COMPLETED);
    	return taskRepository.save(task); // Save the updated task
    }
    
//   For setting all kinds of status
	public Task updateTaskStatus(Long taskId, TaskStatus status) {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found with id " + taskId));
    	
		// Update task status
        task.setStatus(status);
        return taskRepository.save(task); // Save the updated task
	}
    
	public List<Task> getTasksSortedByPriority() {
		return taskRepository.findAll(Sort.by(Sort.Order.asc("priority"))); // for better understanding put "id"
	}

	public List<Task> filterTasks(TaskPriority priority, TaskStatus status) {
		if(priority != null && status != null) {
			return taskRepository.findByPriorityAndStatus(priority, status);
		} else if(priority != null) {
			return taskRepository.findByPriority(priority);
		} else if(status != null) {
			return taskRepository.findByStatus(status);
		}
		return taskRepository.findAll(); // Return all tasks if no filters are applied
	}
	
	//fetch tasks with comments
	public TaskDto convertToDto(Task task) {
		// Convert Task entity to TaskDTO
		TaskDto taskDto = modelMapper.map(task, TaskDto.class);
		
		// Manually map comments list, as ModelMapper can't map nested lists by default
		taskDto.setComments(task.getComments().stream()
				.map(this::convertToCommentDto)
				.collect(Collectors.toList())
				);
		
		return taskDto;
	}
	
	//fetch tasks with comments
	private CommentDto convertToCommentDto(Comment comment) {
		// Convert Comment entity to CommentDTO
		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
		
		// Set the userName manually since it's not directly mapped in the CommentDTO
		User user = comment.getUser();
		if(user != null) {
			commentDto.setUserName(user.getName());
		}
		
		return commentDto;
	}
}
