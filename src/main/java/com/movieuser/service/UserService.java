package com.movieuser.service;

import com.movieuser.exception.UserNotFoundException;
import com.movieuser.model.User;
import com.movieuser.model.CustomerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private FileStorageService fileStorageService;

    public User registerUser(String name, String email, String password) throws IOException {
        if (fileStorageService.getUserByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new CustomerUser(UUID.randomUUID().toString(), name, email, password);
        fileStorageService.saveUser(user);
        return user;
    }

    public User getUserById(String id) throws IOException {
        User user = fileStorageService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        return user;
    }

    public User getUserByEmail(String email) throws IOException {
        User user = fileStorageService.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public List<User> getAllUsers() throws IOException {
        return fileStorageService.getAllUsers();
    }

    public User updateUser(String id, String name, String email, String password) throws IOException {
        User existingUser = getUserById(id);
        existingUser.setName(name);
        existingUser.setEmail(email);
        existingUser.setPassword(password);
        fileStorageService.updateUser(existingUser);
        return existingUser;
    }

    public void deleteUser(String id) throws IOException {
        if (fileStorageService.getUserById(id) == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        fileStorageService.deleteUser(id);
    }
}