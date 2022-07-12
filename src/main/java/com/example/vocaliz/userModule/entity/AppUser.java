package com.example.vocaliz.userModule.entity;

import com.example.vocaliz.categoryModule.entity.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@Document(collection = "user")
public class AppUser {
    @Id
    String userId;
    String email;
    String password;
    String verifyCode;
    Boolean isActivate;
    Category category;
}
