package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

   
    public List<User> getAllUsers(){
    	return userRepository.findAll();
    }
    
    public Optional<User> getUserById(int id){
    	if(userRepository.findById(id) != null) {
    		return userRepository.findById(id);
    	}
    	return Optional.empty();
    	
    }
    public String registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Username already exists!";
        }
        userRepository.save(user);
        return "User registered successfully!";
    }

 
    public boolean verifyUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }


    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(oldPassword)) {
            user.get().setPassword(newPassword);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    public String forgetPassword(String username, String dob) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getDob().toString().equals(dob)) {
            return "Your password is: " + user.get().getPassword();
        }
        return "Invalid username or date of birth!";
    }
    
    
    
    public boolean updateUser(int id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (updatedUser.getBalance() != null) { // Ensure balance is not null
                user.setBalance(updatedUser.getBalance());
            }
            userRepository.save(user);
            return true;
        }
        return false;
    }

	public boolean updateBalance(int id, double balance) {
		Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setBalance(user.getBalance()+balance);
            userRepository.save(user);
            return true;
        }
        return false;
	}


}
