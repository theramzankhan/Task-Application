package com.example.demo.UserController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.EncriptDecript;
import com.example.demo.entity.TaskPriority;
import com.example.demo.entity.User;
import com.example.demo.entity.UserStatus;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private EncriptDecript encriptDecript;
	    
	    @Autowired
	    private UserRepository userRepository;

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
	    @GetMapping("/status/{status}")
	    public List<User> getUserByStatus(@PathVariable UserStatus status) {
	    	return userService.getUserByStatus(status);
	    }
	    
	    @PutMapping("/{userId}/status")
	    public ResponseEntity<User> updateUserStatus(@PathVariable Integer userId, @RequestParam UserStatus status) {
	    	User updatedUser = userService.markUserStatus(userId, status);
	    	return ResponseEntity.ok(updatedUser);
	    }
	    
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
	    
	    @PostMapping("/{adminId}/assign-role/{userId}")
	    public String assignRoleToUser(@PathVariable Integer adminId, @PathVariable Integer userId, @RequestParam String roleName) {
	    	// Ensure that only admins can assign roles
	    	 User adminUser = userRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Useer not found"));
	    	 System.out.println("Admin Role: " + adminUser.getRole().getName());
	    	 if(!"ADMIN".equals(adminUser.getRole().getName().trim().toUpperCase())) {
	    		 throw new RuntimeException("Only admins can assign roles");
	    	 }
	    	 
	    	 userService.assignRoleToUser(userId, roleName);
	    	 return "Role " + roleName + " assigned to user " + userId;
	    }

}
