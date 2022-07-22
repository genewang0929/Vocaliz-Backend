package com.example.vocaliz.categoryModule.service;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.exception.*;
import com.example.vocaliz.repository.*;
import com.example.vocaliz.userModule.entity.*;
import com.example.vocaliz.userModule.service.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import com.example.vocaliz.vocabularyModule.service.*;
import org.bson.types.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.crossstore.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private VocabularyService vocabularyService;

    public List<Category> getAllCategories(String email) {
        return categoryRepository.findAllByCreatorEmail(email);
    }

    public Category getACategory(String categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Can't find category."));
    }

    public Category createACategory(String email, String categoryName) {
        //TODO: add error message
        if (categoryRepository.existsByCategoryNameAndCreatorEmail(categoryName, email))
            return null;
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setVocabularies(new ArrayList<>());
        category.setCreatorEmail(email);
        return categoryRepository.insert(category);
    }

    public Category renameCategory(String categoryId, String newCategoryName) {
        Category category = getACategory(categoryId);
        category.setCategoryName(newCategoryName);
        return categoryRepository.save(category);
    }

    public void deleteCategory(String categoryId) {
        Category category = getACategory(categoryId);
        for (String vocabularyId : category.getVocabularies())
            vocabularyService.deleteVocabulary(vocabularyId);
        categoryRepository.delete(category);
    }

}
