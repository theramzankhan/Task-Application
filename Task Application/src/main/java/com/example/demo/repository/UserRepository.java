package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.TaskPriority;
import com.example.demo.entity.User;
import com.example.demo.entity.UserStatus;

public interface UserRepository extends JpaRepository<User, Integer> {
	
//	List<User> findByPriority(TaskPriority taskPriority);
	List<User> findByStatus(UserStatus userStatus);
	Optional<User> findById(Integer id);
	User findByName(String name);
	
	@Query(value = "SELECT * FROM user " +
				   "WHERE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
				   "OR LOWER(email) LIKE LOWER(CONCAT('%', :keyword, '%'))",
		   nativeQuery = true)
	List<User> searchUsers(@Param("keyword") String keyword);
	
}
