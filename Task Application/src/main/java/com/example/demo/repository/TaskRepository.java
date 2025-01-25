package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import com.example.demo.entity.TaskStatus;
import com.example.demo.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUserId(Integer userId);
	List<Task> findByStatus(TaskStatus status);
	List<Task> findByPriority(TaskPriority priority);
	List<Task> findByPriorityAndStatus(TaskPriority priority, TaskStatus status);
	List<Task> findByUserAndStarred(User user, boolean starred);
	
	@Query(value = "SELECT * FROM task " +
				   "WHERE LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
				   "OR LOWER(description) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
				   "OR LOWER(status) LIKE LOWER(CONCAT('%', :keyword, '%'))",
		   nativeQuery = true)
	List<Task> searchTasks(@Param("keyword") String keyword);
}
