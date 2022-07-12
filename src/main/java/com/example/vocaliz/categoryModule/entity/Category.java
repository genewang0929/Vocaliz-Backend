package com.example.vocaliz.categoryModule.entity;

import com.example.vocaliz.vocabularyModule.entity.*;
import lombok.*;
import org.springframework.data.annotation.*;

import java.util.*;

@Data
public class Category {
    @Id
    String categoryId;
    String categoryName;
    List<Vocabulary> vocabulary;
}
