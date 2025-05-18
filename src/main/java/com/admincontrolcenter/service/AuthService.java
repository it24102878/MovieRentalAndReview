package com.admincontrolcenter.service;

import com.admincontrolcenter.model.Authentication;
import com.admincontrolcenter.model.PasswordAuth;
import com.admincontrolcenter.util.FileHandler;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final FileHandler fileHandler;
    private static final String DEFAULT_ADMIN_EMAIL = "admin@moviesphere.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "Admin123!";

    public AuthService() {
        this.fileHandler = new FileHandler("auth.txt");
    }

    @PostConstruct
    public void init() throws Exception {
        // Initialize default admin account if it doesn't exist
        List<String> lines = fileHandler.readLines();
        boolean adminExists = lines.stream().anyMatch(line -> line.startsWith(DEFAULT_ADMIN_EMAIL + ","));
        if (!adminExists) {
            String adminRecord = DEFAULT_ADMIN_EMAIL + ",Admin," + DEFAULT_ADMIN_PASSWORD + ",true";
            fileHandler.appendLine(adminRecord);
        }
    }

    public void createAuth(PasswordAuth auth) throws Exception {
        if (!auth.validate()) {
            throw new Exception("Password and Confirm Password do not match");
        }
        List<String> lines = fileHandler.readLines();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(auth.getEmail())) {
                throw new Exception("User already exists");
            }
        }
        String record = auth.getEmail() + "," + auth.getFullName() + "," + auth.getPassword() + "," + auth.isAdmin();
        fileHandler.appendLine(record);
    }

    public String verifyAuth(String email, String password) throws Exception {
        List<String> lines = fileHandler.readLines();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 4 && parts[0].equals(email) && parts[2].equals(password)) {
                boolean isAdmin = Boolean.parseBoolean(parts[3]);
                return isAdmin ? "admin" : "user";
            }
        }
        return null;
    }

    public void updateAuth(PasswordAuth auth) throws Exception {
        List<String> lines = fileHandler.readLines();
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts[0].equals(auth.getEmail())) {
                lines.set(i, auth.getEmail() + "," + auth.getFullName() + "," + auth.getPassword() + "," + auth.isAdmin());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("User not found");
        }
        fileHandler.writeLines(lines);
    }

    public void deleteAuth(String email) throws Exception {
        List<String> lines = fileHandler.readLines();
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts[0].equals(email)) {
                lines.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("User not found");
        }
        fileHandler.writeLines(lines);
    }

    public List<PasswordAuth> getAllUsers() throws Exception {
        List<String> lines = fileHandler.readLines();
        List<PasswordAuth> users = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String email = parts[0];
                String fullName = parts[1];
                boolean isAdmin = Boolean.parseBoolean(parts[3]);

                // Create PasswordAuth object with masked password for security
                PasswordAuth user = new PasswordAuth(email, fullName, "********", "********", isAdmin);
                users.add(user);
            }
        }

        return users;
    }
}
