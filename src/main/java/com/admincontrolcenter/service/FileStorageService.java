package com.admincontrolcenter.service;

import com.admincontrolcenter.exception.FileOperationException;
import com.admincontrolcenter.exception.ResourceNotFoundException;
import com.admincontrolcenter.exception.ValidationException;
import com.admincontrolcenter.model.Admin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FileStorageService {

    private final String adminsFile;
    private final String activityLog;
    private static final Logger LOGGER = Logger.getLogger(FileStorageService.class.getName());

    public FileStorageService(@Value("${app.data.directory}") String dataDirectory) {
        this.adminsFile = dataDirectory + "/admins.txt";
        this.activityLog = dataDirectory + "/activity.log";

        // Ensure the data directory exists
        try {
            Files.createDirectories(Paths.get(dataDirectory));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating data directory: " + e.getMessage(), e);
        }
    }

    public void saveAdmin(Admin admin) throws IOException {
        if (admin == null) {
            throw new ValidationException("Admin cannot be null");
        }

        try {
            // Ensure the file exists
            Path adminFilePath = Paths.get(adminsFile);
            if (!Files.exists(adminFilePath)) {
                Files.createFile(adminFilePath);
            }

            // Append admin details to admins.txt
            Files.writeString(
                    adminFilePath,
                    admin.toFileString() + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
            );
            logActivity("Admin created: " + admin.getEmail());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving admin: " + e.getMessage(), e);
            throw FileOperationException.writeError(adminsFile, e);
        }
    }

    public List<Admin> getAllAdmins() throws IOException {
        List<Admin> admins = new ArrayList<>();
        Path adminFilePath = Paths.get(adminsFile);

        try {
            if (!Files.exists(adminFilePath)) {
                return admins; // Return empty list if file doesn't exist
            }

            List<String> lines = Files.readAllLines(adminFilePath);
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    try {
                        admins.add(Admin.fromFileString(line));
                    } catch (Exception e) {
                        LOGGER.warning("Invalid admin entry found: " + line);
                        // Continue processing other lines
                    }
                }
            }
            return admins;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading admin file: " + e.getMessage(), e);
            throw FileOperationException.readError(adminsFile, e);
        }
    }

    public void updateAdmin(Admin updatedAdmin) throws IOException {
        if (updatedAdmin == null) {
            throw new ValidationException("Updated admin cannot be null");
        }

        try {
            List<Admin> admins = getAllAdmins();
            boolean adminFound = false;

            List<String> updatedLines = new ArrayList<>();
            for (Admin admin : admins) {
                if (admin.getId().equals(updatedAdmin.getId())) {
                    updatedLines.add(updatedAdmin.toFileString());
                    adminFound = true;
                } else {
                    updatedLines.add(admin.toFileString());
                }
            }

            if (!adminFound) {
                throw new ResourceNotFoundException("Admin", "id", updatedAdmin.getId());
            }

            Files.write(Paths.get(adminsFile), String.join(System.lineSeparator(), updatedLines).getBytes());
            logActivity("Admin updated: " + updatedAdmin.getEmail());
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error updating admin: " + e.getMessage(), e);
            throw FileOperationException.writeError(adminsFile, e);
        }
    }

    public void deleteAdmin(String id) throws IOException {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("Admin ID cannot be null or empty");
        }

        try {
            List<Admin> admins = getAllAdmins();
            Admin deletedAdmin = null;

            for (Admin admin : admins) {
                if (admin.getId().equals(id)) {
                    deletedAdmin = admin;
                    break;
                }
            }

            if (deletedAdmin == null) {
                throw new ResourceNotFoundException("Admin", "id", id);
            }

            admins.removeIf(admin -> admin.getId().equals(id));
            List<String> updatedLines = new ArrayList<>();

            for (Admin admin : admins) {
                updatedLines.add(admin.toFileString());
            }

            Files.write(Paths.get(adminsFile), String.join(System.lineSeparator(), updatedLines).getBytes());
            logActivity("Admin deleted: " + deletedAdmin.getEmail());
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error deleting admin: " + e.getMessage(), e);
            throw FileOperationException.writeError(adminsFile, e);
        }
    }

    public void logActivity(String activity) throws IOException {
        if (activity == null || activity.trim().isEmpty()) {
            return; // Don't log empty activities
        }

        try {
            Path logPath = Paths.get(activityLog);
            Path parentDir = logPath.getParent();

            if (!Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            String logEntry = java.time.LocalDateTime.now() + ": " + activity + System.lineSeparator();
            Files.writeString(
                    logPath,
                    logEntry,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error logging activity: " + e.getMessage(), e);
            // Don't throw exception for logging failures
        }
    }

    public List<String> getActivityLogs() throws IOException {
        try {
            Path logPath = Paths.get(activityLog);
            if (!Files.exists(logPath)) {
                return new ArrayList<>(); // Return empty list if file doesn't exist
            }
            return Files.readAllLines(logPath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading activity logs: " + e.getMessage(), e);
            throw FileOperationException.readError(activityLog, e);
        }
    }
}
