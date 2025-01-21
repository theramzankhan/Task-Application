package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TaskPriority;
import com.example.demo.entity.UserStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
//    
//    public List<User> getUserByPriority(TaskPriority priority) {
//    	return userRepository.findByPriority(priority);
//    }
//    
//    public List<User> getUserByStatus(UserStatus status) {
//    	return userRepository.findByStatus(status);
//    }
}

