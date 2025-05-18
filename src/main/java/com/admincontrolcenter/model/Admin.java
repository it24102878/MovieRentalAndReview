package com.admincontrolcenter.model;

public class Admin extends User {
    private Permission permission;

    public Admin(String id, String name, String email, Permission permission) {
        super(id, name, email);
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public String toFileString() {
        return getId() + "," + getName() + "," + getEmail() + "," + permission.name();
    }

    public static Admin fromFileString(String line) {
        String[] parts = line.split(",");
        return new Admin(parts[0], parts[1], parts[2], Permission.valueOf(parts[3]));
    }
}