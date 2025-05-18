package com.moviesphere.service;

import com.moviesphere.model.Authentication;
import com.moviesphere.model.PasswordAuth;
import com.moviesphere.util.FileHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final FileHandler fileHandler;

    public AuthService() {
        this.fileHandler = new FileHandler("auth.txt"); // File will be created in the project root
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
        String record = auth.getEmail() + "," + auth.getFullName() + "," + auth.getPassword();
        fileHandler.appendLine(record);
    }

    public boolean verifyAuth(String email, String password) throws Exception {
        List<String> lines = fileHandler.readLines();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(email) && parts[2].equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void updateAuth(PasswordAuth auth) throws Exception {
        List<String> lines = fileHandler.readLines();
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts[0].equals(auth.getEmail())) {
                lines.set(i, auth.getEmail() + "," + auth.getFullName() + "," + auth.getPassword());
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
}