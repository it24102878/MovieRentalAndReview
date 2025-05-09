package com.Movie.AdminManagment.service;

import com.Movie.AdminManagment.model.Admin;
import com.Movie.AdminManagment.util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

public class AdminService {
    private static final String ADMIN_FILE = "admins.txt";
    private static final String LOG_FILE = "admin_logs.txt";

    public void createAdmin(Admin admin) {
        FileUtil.appendLine(ADMIN_FILE, admin.toFileString());
        logActivity("Created admin: " + admin.getUsername());
    }

    public List<Admin> getAllAdmins() {
        return FileUtil.readLines(ADMIN_FILE).stream()
                .map(Admin::fromFileString)
                .collect(Collectors.toList());
    }

    public void updateAdmin(Admin updatedAdmin) {
        List<Admin> admins = getAllAdmins();
        List<String> updated = admins.stream()
                .map(a -> a.getUsername().equals(updatedAdmin.getUsername()) ? updatedAdmin.toFileString() : a.toFileString())
                .collect(Collectors.toList());
        FileUtil.writeLines(ADMIN_FILE, updated);
        logActivity("Updated admin: " + updatedAdmin.getUsername());
    }

    public void deleteAdmin(String username) {
        List<Admin> admins = getAllAdmins();
        List<String> filtered = admins.stream()
                .filter(a -> !a.getUsername().equals(username))
                .map(Admin::toFileString)
                .collect(Collectors.toList());
        FileUtil.writeLines(ADMIN_FILE, filtered);
        logActivity("Deleted admin: " + username);
    }

    public List<String> getLogs() {
        return FileUtil.readLines(LOG_FILE);
    }

    private void logActivity(String message) {
        FileUtil.appendLine(LOG_FILE, new Date() + ": " + message);
    }
}
