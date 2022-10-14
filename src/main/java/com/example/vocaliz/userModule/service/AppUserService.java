package com.example.vocaliz.userModule.service;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.categoryModule.service.*;
import com.example.vocaliz.repository.*;
import com.example.vocaliz.userModule.entity.*;
import com.example.vocaliz.verificationModule.service.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.crossstore.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AppUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    @Lazy(value = true)
    private MailService mailService = new MailService();
    private BCryptPasswordEncoder passwordEncoder;

    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean hasExitUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public AppUser getUserById(String id) {
        return userRepository.findByUserId(id);
    }

    public AppUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public AppUser createUser(AppUser request) {
        // check email has existed
        if (hasExitUserByEmail(request.getEmail())) {
            return null;
        }
        AppUser appUser = new AppUser();
        appUser.setEmail(request.getEmail());
        appUser.setName(request.getName());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setIsActivate(false);
        // send code
        String code = randomCode();
        appUser.setVerifyCode(code);
        mailService.sendEmailToUser(request.getEmail(), "Vocaliz verification code" , code);

        // 建Default資料夾
        categoryService.createACategory(request.getEmail(), "Default");

        return userRepository.insert(appUser);
    }

    public AppUser replaceUser(AppUser request) {
        AppUser user = new AppUser();

        user.setUserId(request.getUserId());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setVerifyCode(request.getVerifyCode());
        user.setIsActivate(request.getIsActivate());
        return userRepository.save(user);
    }

    public static String randomCode() {
        char[] chars = new char[4];
        Random rnd = new Random();
        for (int i = 0; i < 4; i++) {
            chars[i] = (char) ('0' + rnd.nextInt(10));
        }
        return new String(chars);
    }

    public void replacePassword(String email, String genRandomPassword) {
        AppUser user = getUserByEmail(email);
        user.setPassword(passwordEncoder.encode(genRandomPassword));
        replaceUser(user);
    }
}
