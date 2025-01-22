package com.example.demo.entity;

import java.time.LocalDateTime;

//Why Separate Tables?
//Normalization:
//
//Storing comments in a separate table avoids redundancy. Comments can grow independently from tasks without bloating the Task table.
//Flexibility:
//
//Separate tables allow flexibility in querying, updating, or deleting comments without directly affecting tasks.
//Performance:
//
//Loading comments only when necessary (e.g., fetching them for a specific task) reduces overhead, especially if tasks without comments are frequently accessed.

public class CommentDto {
	
	private Long id;
	private String message;
	private LocalDateTime createdAt;
	private String userName;   // Add userName to return in the response
	
	//getters and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
