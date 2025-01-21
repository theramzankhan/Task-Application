package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TaskPriority;
import com.example.demo.entity.UserStatus;
import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
//	List<User> findByPriority(TaskPriority taskPriority);
//	List<User> findByStatus(UserStatus userStatus);
	
}
