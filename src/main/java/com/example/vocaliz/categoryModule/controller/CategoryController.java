package com.example.vocaliz.categoryModule.controller;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.categoryModule.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/category",produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/allCategories/{email}")
    public ResponseEntity<Object> getAllCategories(@PathVariable("email") String email) {
        List<Category> categories = categoryService.getAllCategories(email);
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categories);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> getACategory(@PathVariable("categoryId") String categoryId) {
        Category category = categoryService.getACategory(categoryId);
        Map<String, Object> map = new HashMap<>();
        map.put("category", Objects.requireNonNullElse(category, "category not found"));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/{email}")
    public ResponseEntity<Object> createACategory(@PathVariable("email") String email,
                                                  @RequestBody Map<String, String> request) {
        String categoryName = request.get("categoryName");
        Category category = categoryService.createACategory(email, categoryName);
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/rename/{categoryId}")
    public ResponseEntity<Object> renameACategory(@PathVariable("categoryId") String categoryId,
                                                  @RequestBody Map<String, String> request) {
        String newCategoryName = request.get("newCategoryName");
        Category category = categoryService.renameCategory(categoryId, newCategoryName);
        Map<String, Object> map = new HashMap<>();
        map.put("category", Objects.requireNonNullElse(category, "category not found"));
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
