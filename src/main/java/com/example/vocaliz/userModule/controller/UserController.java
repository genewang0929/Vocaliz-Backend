package com.example.vocaliz.userModule.controller;

import com.example.vocaliz.userModule.entity.AppUser;
import com.example.vocaliz.userModule.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private AppUserService userService;

    @GetMapping("/allUsers")
    public ResponseEntity<Object> getAllUsers() {
        List<AppUser> users = userService.getAllUsers();
        Map<String, Object> map = new HashMap<>();
        map.put("users", users);
        return ResponseEntity.ok(map);
    }

}
