package com.admincontrolcenter.service;

import com.admincontrolcenter.model.Admin;
import com.admincontrolcenter.model.Permission;
import com.admincontrolcenter.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private FileStorageService fileStorageService;

    public Admin registerAdmin(String name, String email, Permission permission) throws IOException {
        String id = IdGenerator.generateId();
        Admin admin = new Admin(id, name, email, permission);
        fileStorageService.saveAdmin(admin);
        return admin;
    }

    public List<Admin> getAllAdmins() throws IOException {
        return fileStorageService.getAllAdmins();
    }

    public Admin updateAdmin(String id, String name, String email, Permission permission) throws IOException {
        Admin updatedAdmin = new Admin(id, name, email, permission);
        fileStorageService.updateAdmin(updatedAdmin);
        return updatedAdmin;
    }

    public void deleteAdmin(String id) throws IOException {
        fileStorageService.deleteAdmin(id);
    }

    public List<String> getActivityLogs() throws IOException {
        return fileStorageService.getActivityLogs();
    }
}