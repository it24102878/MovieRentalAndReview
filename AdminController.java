package com.Movie.AdminManagment.controller;

import com.Movie.AdminManagment.model.Admin;
import com.Movie.AdminManagment.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService = new AdminService();

    @PostMapping("/create")
    public String createAdmin(@RequestBody Admin admin) {
        adminService.createAdmin(admin);
        return "Admin created successfully.";
    }

    @GetMapping("/all")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PutMapping("/update")
    public String updateAdmin(@RequestBody Admin admin) {
        adminService.updateAdmin(admin);
        return "Admin updated successfully.";
    }

    @DeleteMapping("/delete/{username}")
    public String deleteAdmin(@PathVariable String username) {
        adminService.deleteAdmin(username);
        return "Admin deleted successfully.";
    }

    @GetMapping("/logs")
    public List<String> getLogs() {
        return adminService.getLogs();
    }
}
