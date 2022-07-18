package com.example.vocaliz.categoryModule.service;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.repository.*;
import com.example.vocaliz.userModule.entity.*;
import com.example.vocaliz.userModule.service.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import org.bson.types.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    AppUserService appUserService;
    @Autowired
    UserRepository userRepository;

    public List<Category> getAllCategories(String email) {
        AppUser appUser = appUserService.getUserByEmail(email);
        return appUser.getCategory();
    }

    public Category getACategory(String email, String categoryId) {
        AppUser appUser = appUserService.getUserByEmail(email);
        List<Category> categories = appUser.getCategory();
        for (Category category : categories) {
            if (category.getCategoryId().equals(categoryId))
                return category;
        }
        return null;
    }

    public Category createACategory(String email, String categoryName) {
        AppUser appUser = appUserService.getUserByEmail(email);
        List<Vocabulary> vocabulary = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setCategoryId(new ObjectId().toString());
        category.setCategoryName(categoryName);
        category.setVocabulary(vocabulary);
        categories.add(category);
        appUser.setCategory(categories);
        userRepository.save(appUser);
        return category;
    }

    public Category renameCategory(String email, String categoryId, String newCategoryName) {
        AppUser appUser = appUserService.getUserByEmail(email);
        List<Category> categories = appUser.getCategory();
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            if (category.getCategoryId().equals(categoryId)) {
                category.setCategoryName(newCategoryName);
                categories.set(i, category);
                appUser.setCategory(categories);
                userRepository.save(appUser);
                return category;
            }
        }

        return null;
    }

    public List<Category> deleteCategory(String email, String categoryId) {
        AppUser appUser = appUserService.getUserByEmail(email);
        List<Category> categories = appUser.getCategory();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId().equals(categoryId)) {
                categories.remove(i);
                break;
            }
        }
        appUser.setCategory(categories);
        userRepository.save(appUser);
        return categories;
    }

}
