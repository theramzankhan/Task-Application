package com.example.demo.UserController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskDto;
import com.example.demo.entity.TaskPriority;
import com.example.demo.entity.TaskStatus;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;

@RestController
@RequestMapping("/api/users/{userId}/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@PostMapping
    public ResponseEntity<Task> createTask(@PathVariable Integer userId, @RequestBody Task task) {
        Task createdTask = taskService.createTaskForUser(userId, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTaskBySatus(@PathVariable TaskStatus status) {
        List<Task> tasks = taskService.getTaskBySatus(status);
        return ResponseEntity.ok(tasks);
    }
    
    @PutMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long taskId) {
    	Task updatedTask = taskService.markTaskAsCompleted(taskId);
    	return ResponseEntity.ok(updatedTask);
    }
    
    @PutMapping("/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
    	Task updatedTask = taskService.updateTaskStatus(taskId, status);
    	return ResponseEntity.ok(updatedTask);
    }
    
    @GetMapping("/priority")
    public ResponseEntity<List<Task>> getTasksSortedByPriority() {
        List<Task> tasks = taskService.getTasksSortedByPriority();
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/filter")
    public ResponseEntity<List<Task>> filterTasks(
    		@RequestParam(required = false) TaskPriority priority,
    		@RequestParam(required = false) TaskStatus status
    		) {
    	List<Task> tasks = taskService.filterTasks(priority, status);
    	return ResponseEntity.ok(tasks);
    }
    
    //fetch tasks with comments
    @GetMapping("{taskId}")
    public TaskDto getTaskWithComments(@PathVariable Long taskId) {
    	Optional<Task> task = taskRepository.findById(taskId);
    	if(task.isPresent()) {
    		return taskService.convertToDto(task.get());
    	} else {
    		throw new RuntimeException("Task not found for id: " + taskId);
    	}
    }
}