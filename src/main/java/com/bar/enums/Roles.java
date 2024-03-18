package com.bar.enums;

public enum Roles {
    SYSTEM("system"),
    USER("user"),
    ASSISTANCE("assistant");
    private String role;

    Roles(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
