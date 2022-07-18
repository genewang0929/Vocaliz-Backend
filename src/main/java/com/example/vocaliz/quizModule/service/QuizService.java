package com.example.vocaliz.quizModule.service;

import com.example.vocaliz.categoryModule.service.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import com.example.vocaliz.vocabularyModule.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class QuizService {
    @Autowired
    private VocabularyService vocabularyService;
    @Autowired
    private CategoryService categoryService;

    public List<Vocabulary> getQuizVocabularies(String email, String categoryId, int rankLV) {

    }
}
