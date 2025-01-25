package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByTaskId(Long taskId);
	
	@Query(value = "SELECT * FROM comment " +
				   "WHERE LOWER(message) LIKE LOWER(CONCAT('%', :keyword, '%'))",
		   nativeQuery = true)
	List<Comment> searchComments(@Param("keyword") String keyword);

}
