package com.admincontrolcenter.controller;

import com.admincontrolcenter.model.Admin;
import com.admincontrolcenter.model.Permission;
import com.admincontrolcenter.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam Permission permission) throws IOException {
        Admin admin = adminService.registerAdmin(name, email, permission);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() throws IOException {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(
            @PathVariable String id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam Permission permission) throws IOException {
        Admin updatedAdmin = adminService.updateAdmin(id, name, email, permission);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String id) throws IOException {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logs")
    public ResponseEntity<List<String>> getActivityLogs() throws IOException {
        return ResponseEntity.ok(adminService.getActivityLogs());
    }
}