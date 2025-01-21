package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import com.example.demo.entity.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUserId(Integer userId);
	List<Task> findByStatus(TaskStatus status);
	List<Task> findByPriority(TaskPriority priority);
	List<Task> findByPriorityAndStatus(TaskPriority priority, TaskStatus status);
}
