package com.example.demo.UserController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.EncriptDecript;
import com.example.demo.entity.TaskPriority;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private EncriptDecript encriptDecript;

	    @GetMapping
	    public List<User> getAllUsers() {
	        return userService.getAllUsers();
	    }

	    @GetMapping("/{id}")
	    public Optional<User> getUserById(@PathVariable Integer id) {
	        return userService.getUserById(id);
	    }

	    @PostMapping
	    public User createUser(@RequestBody User user) {
	        return userService.saveUser(user);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteUser(@PathVariable Integer id) {
	        userService.deleteUser(id);
	    }
	    
//	    @GetMapping("/priority/{priority}")
//	    public List<User> getUserByPriority(@PathVariable TaskPriority priority) {
//	    	return userService.getUserByPriority(priority);
//	    }
//	    
//	    @GetMapping("/status/{status}")
//	    public List<User> getUserByStatus(@PathVariable UserStatus status) {
//	    	return userService.getUserByStatus(status);
//	    }
	    
	 // Endpoint to encrypt and save the username //separately but will encrypt while creating user using post mapping
	    //will do it while user inserting or setting profile which makes sense
//	    @GetMapping("/encrypt-password/{userId}")
//	    public String encryptpassword(@PathVariable Integer userId) {
//	        encriptDecript.encryptAndSavePassword(userId);
//	        return "User password encrypted and saved for user ID: " + userId;
//	    }
//
//	    // Endpoint to decrypt and retrieve the user password
	    @GetMapping("/decrypt-password/{userId}")
	    public String decryptPassword(@PathVariable Integer userId) {
	        return "Decrypted Password: " + encriptDecript.decryptPassword(userId);
	    }

}
