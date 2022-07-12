package com.example.vocaliz.verificationModule.entity;

import lombok.*;

@Data
public class AuthRequest {
    private String password;
    private String newPassword;
    private String email;
}
