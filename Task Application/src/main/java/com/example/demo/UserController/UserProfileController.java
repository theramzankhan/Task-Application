package com.example.demo.UserController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;

@RestController
@RequestMapping("/api/user-profile")
public class UserProfileController {
	
	@Autowired
	private UserProfileService userProfileService;
	
	// Endpoint to update user profile details
	@PutMapping("/{userId}")
	public UserProfile updateUserProfile(@PathVariable Integer userId, @RequestParam String bio, @RequestParam String location) {
		return userProfileService.updateUserProfile(userId, bio, location);
	}
	
	// Endpoint to upload avatar
	@PostMapping("/upload-avatar/{userId}")
	public String uploadAvatar(@PathVariable Integer userId, @RequestParam("avatar") MultipartFile avatar) throws IOException {
		return userProfileService.uploadAvatar(userId, avatar);
	}
}
