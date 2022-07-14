package com.example.vocaliz.userModule.entity;

import com.example.vocaliz.categoryModule.entity.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

@Data
@Document(collection = "user")
public class AppUser {
    @Id
    String userId;
    String email;
    String password;
    String name;
    String verifyCode;
    Boolean isActivate;
    List<Category> category;
}
