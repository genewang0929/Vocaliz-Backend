package com.example.vocaliz.quizModule.controller;

import com.example.vocaliz.quizModule.service.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "quizWords", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("/{email}/{categoryId}")
    public ResponseEntity<Object> getQuizVocabularies(@PathVariable("email") String email,
                                                      @PathVariable("categoryId") String categoryId,
                                                      @RequestParam(value = "rankLV", defaultValue = "") String rankLV,
                                                      @RequestParam(value = "wordNum", defaultValue = "") String wordNum) {
        List<Vocabulary> vocabulary = quizService.getQuizVocabularies(email, categoryId, rankLV, wordNum);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }
}
