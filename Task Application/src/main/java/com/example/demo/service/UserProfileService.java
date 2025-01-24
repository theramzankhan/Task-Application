package com.example.demo.service;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserProfileService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	// Path to store avatar images
//	private final String avatarDirectory = "D:\\avatars";  //old way
	
//	You can use the @Value annotation in your service class to inject the property value.
	@Value("${avatar.directory}")
	private String avatarDirectory;
	
	public UserProfile updateUserProfile(Integer userId, String bio, String location) {
		logger.info("Updating profile for user with ID: {}", userId);
		// Fetch the user
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
		logger.error("User not found with ID: {}", userId);
		// Find existing profile or create a new one
		UserProfile profile = userProfileRepository.findByUser(user).orElse(new UserProfile());
		profile.setUser(user);
		profile.setBio(bio);
		profile.setLocation(location);
		logger.info("Uploading avatar for user with ID: {}", userId);
		UserProfile updatedProfile = userProfileRepository.save(profile);
		return updatedProfile;
	}
	
	public String uploadAvatar(Integer userId, MultipartFile avatar) throws IOException {
		logger.info("Uploading avatar for user with ID: {}", userId);
		// Check if avatar is valid (e.g., not empty)
		if(avatar.isEmpty()) {
			logger.error("No file uploaded for user with ID: {}", userId);
			throw new IllegalArgumentException("No file uploaded");
		}
		
		// Generate a unique file name for the avatar
		String avatarFileName = userId + "_" + System.currentTimeMillis() + "_" + avatar.getOriginalFilename();
		
		// Save the avatar to the disk
		Path avatarPath = Paths.get(avatarDirectory, avatarFileName);
		logger.debug("Saving avatar to: {}", avatarPath.toString());
		System.out.println("Saving avatar to: " + avatarPath.toString());
		Files.createDirectories(avatarPath.getParent()); // Ensure directory exists
		Files.write(avatarPath, avatar.getBytes());
		logger.info("Avatar saved successfully for user with ID: {}", userId);
		
		// Update the user profile with the avatar URL
		//Fetch the user
		User user = userRepository.findById(userId).orElseThrow(() -> { 
			logger.error("User not found with ID: {}", userId); 
			return  new RuntimeException("user not found");
		});
		
		// Find existing profile or create a new one
		UserProfile profile = userProfileRepository.findByUser(user).orElse(new UserProfile());
		profile.setUser(user);
		profile.setAvatarUrl("/avatars/" + avatarFileName);
		userProfileRepository.save(profile);
		logger.info("Avatar URL updated for user with ID: {}", userId);
		
		return "/avatars/" + avatarFileName;  // Return URL or path of the saved avatar
	}

}
