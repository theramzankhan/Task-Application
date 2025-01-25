package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.EncriptDecript;
import com.example.demo.entity.User;
import com.example.demo.entity.UserStatus;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private EncriptDecript encription;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
    	// Encrypt the password before saving
    	String originalPassword = user.getPassword();
    	String encryptedPassword = encription.encrypt(originalPassword);
    	user.setPassword(encryptedPassword);
    	
    	//Set user status as ACTIVE by default when user is being created
    	user.setStatus(UserStatus.ACTIVE);
    	
    	// Save the user with the encrypted password
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
//    
//    public List<User> getUserByPriority(TaskPriority priority) {
//    	return userRepository.findByPriority(priority);
//    }

    //mark it inactive, make it active by deafult while creating user
    public User markUserStatus(Integer userId, UserStatus status) {
    	User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    	user.setStatus(status);
    	return userRepository.save(user);
    }
    
    public List<User> getUserByStatus(UserStatus status) {
    	return userRepository.findByStatus(status);
    }
}

