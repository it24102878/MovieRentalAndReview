package com.Movie.AdminManagment.model;

public class Admin extends User {
    private String password;
    private String role; // e.g., superadmin, moderator

    public Admin(String username, String email, String password, String role) {
        super(username, email);
        this.password = password;
        this.role = role;
    }

    public String getPassword() { return password; }
    public String getRole() { return role; }

    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }

    public String toFileString() {
        return username + "," + email + "," + password + "," + role;
    }

    public static Admin fromFileString(String line) {
        String[] parts = line.split(",");
        return new Admin(parts[0], parts[1], parts[2], parts[3]);
    }
}
