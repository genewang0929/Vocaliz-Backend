package com.example.vocaliz.repository;

import com.example.vocaliz.categoryModule.entity.*;
import org.springframework.data.mongodb.repository.*;

import java.util.*;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findAllByCreatorEmail(String email);
    Category findByCategoryNameAndCreatorEmail(String categoryName, String email);
    Boolean existsByCategoryNameAndCreatorEmail(String categoryName, String email);
}
