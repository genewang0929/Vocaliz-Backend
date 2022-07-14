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

    @GetMapping("/{email}")
    public ResponseEntity<Object> getAllCategories(@PathVariable("email") String email) {
        List<Category> categories = categoryService.getAllCategories(email);
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categories);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{email}/{categoryId}")
    public ResponseEntity<Object> getACategory(@PathVariable("email") String email,
                                                   @PathVariable("categoryId") String categoryId) {
        Category category = categoryService.getACategory(email, categoryId);
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/{email}")
    public ResponseEntity<Object> createACategory(@PathVariable("email") String email,
                                                  @RequestBody Map<String, String> request) {
        String categoryName = request.get("categoryName");
        List<Category> categories = categoryService.createACategory(email, categoryName);
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categories);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/rename/{email}/{categoryId}")
    public ResponseEntity<Object> renameACategory(@PathVariable("email") String email,
                                                  @PathVariable("categoryId") String categoryId,
                                                  @RequestBody Map<String, String> request) {
        String newCategoryName = request.get("newCategoryName");
        List<Category> categories = categoryService.renameCategory(email, categoryId, newCategoryName);
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categories);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{email}/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("email") String email,
                                                 @PathVariable("categoryId") String categoryId) {
        List<Category> categories = categoryService.deleteCategory(email, categoryId);
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categories);
        return ResponseEntity.ok(map);
    }
}
