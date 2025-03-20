package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){
    	return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
    	Optional<User> user =  userService.getUserById(id);
    	if(user!=null) {
    		return  user.get();
    		
    	}
    	return null;
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        System.out.println("Received request to update user with ID: " + id);
        System.out.println("New user data: " + updatedUser);

        boolean updated = userService.updateUser(id, updatedUser);
        if (updated) {
            return ResponseEntity.ok("User updated successfully.");
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }


    
    
    

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String response = userService.registerUser(user);
        if (response.equals("Username already exists!")) {
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> verifyUser(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        boolean isValid = userService.verifyUser(username, password);
        if (isValid) {
            return ResponseEntity.ok("User verified successfully!");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password.");
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String oldPassword = data.get("oldPassword");
        String newPassword = data.get("newPassword");

        boolean success = userService.changePassword(username, oldPassword, newPassword);
        if (success) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.status(400).body("Old password is incorrect.");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String newPassword = data.get("newPassword");

        boolean success = userService.resetPassword(username, newPassword);
        if (success) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }
}
