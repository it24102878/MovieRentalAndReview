package com.movieuser.service;

import com.movieuser.model.User;
import com.movieuser.model.CustomerUser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {
    private static final String FILE_PATH = "src/main/resources/users.txt";

    public void saveUser(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.format("%s,%s,%s,%s,%s\n",
                    user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getUserType()));
        }
    }

    public List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5 && data[4].equals("Customer")) {
                    users.add(new CustomerUser(data[0], data[1], data[2], data[3]));
                }
            }
        }
        return users;
    }

    public User getUserById(String id) throws IOException {
        for (User user : getAllUsers()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByEmail(String email) throws IOException {
        for (User user : getAllUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void updateUser(User updatedUser) throws IOException {
        List<User> users = getAllUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                if (user.getId().equals(updatedUser.getId())) {
                    writer.write(String.format("%s,%s,%s,%s,%s\n",
                            updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(),
                            updatedUser.getPassword(), updatedUser.getUserType()));
                } else {
                    writer.write(String.format("%s,%s,%s,%s,%s\n",
                            user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getUserType()));
                }
            }
        }
    }

    public void deleteUser(String id) throws IOException {
        List<User> users = getAllUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                if (!user.getId().equals(id)) {
                    writer.write(String.format("%s,%s,%s,%s,%s\n",
                            user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getUserType()));
                }
            }
        }
    }
}