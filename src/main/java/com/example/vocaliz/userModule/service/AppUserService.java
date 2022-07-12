package com.example.vocaliz.userModule.service;

import com.example.vocaliz.categoryModule.entity.*;
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
//    @Autowired
//    @Lazy(value = true)
//    private MailService mailService = new MailService();
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
        appUser.setVerifyCode(randomCode());
        appUser.setIsActivate(false);
        List<Vocabulary> vocabulary = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setCategoryName("Default");
        category.setVocabulary(vocabulary);
        categories.add(category);
        appUser.setCategory(categories);
        userRepository.insert(appUser);
        // TODO: create Buy and Favorite and default folder

        appUser = getUserById(appUser.getUserId());
        return appUser;
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


}
