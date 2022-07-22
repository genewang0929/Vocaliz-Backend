package com.example.vocaliz.categoryModule.entity;

import com.example.vocaliz.vocabularyModule.entity.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

@Data
@Document(collection = "category")
public class Category {
    @Id
    String categoryId;
    String categoryName;
    List<String> vocabularies;
    String creatorEmail;
}
