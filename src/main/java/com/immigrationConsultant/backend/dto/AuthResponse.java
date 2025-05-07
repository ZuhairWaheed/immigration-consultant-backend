package com.immigrationConsultant.backend.dto;

public class AuthResponse {
    private String token;
    private String email;
    private String role;

    public AuthResponse(String token, String email, String roleName) {
        this.token = token;
        this.email = email;
        this.role = roleName;
    }
}
