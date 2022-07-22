package com.example.vocaliz.vocabularyModule.controller;

import com.example.vocaliz.vocabularyModule.entity.*;
import com.example.vocaliz.vocabularyModule.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
public class VocalularyController {
    @Autowired
    private VocabularyService vocabularyService;

    @GetMapping("/word/{wordId}")
    public ResponseEntity<Object> getAVocabulary(@PathVariable("wordId") String wordId) {
        Vocabulary vocabulary = vocabularyService.getAVocabulary(wordId);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/allWords/{categoryId}/{offset}/{pageNumber}")
    public ResponseEntity<Object> getAllVocabulariesInCategory(@PathVariable("categoryId") String categoryId,
                                                               @PathVariable("offset") int offset,
                                                               @PathVariable("pageNumber") int pageNumber) {
        Pages vocabulary = vocabularyService.getAllVocabulariesInCategory(categoryId, offset, pageNumber);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/rankLVWords/{categoryId}/{rankLV}/{offset}/{pageNumber}")
    public ResponseEntity<Object> getVocabulariesByRankLVInCategory(@PathVariable("categoryId") String categoryId,
                                                                    @PathVariable("rankLV") int rankLV,
                                                                    @PathVariable("offset") int offset,
                                                                    @PathVariable("pageNumber") int pageNumber) {
        Pages vocabulary = vocabularyService.getVocabulariesByRankLVInCategory(categoryId, rankLV, offset, pageNumber);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/word/{categoryId}")
    public ResponseEntity<Object> createAVocabulary(@PathVariable("categoryId") String categoryId,
                                                    @RequestBody Vocabulary request) {
        Vocabulary vocabulary = vocabularyService.createVocabulary(categoryId, request);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", vocabulary);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/word/{wordId}")
    public ResponseEntity<Object> updateVocabulary(@PathVariable("wordId") String wordId,
                                                   @RequestBody Vocabulary request) {
        Vocabulary vocabulary = vocabularyService.updateVocabulary(wordId, request);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }

    @PutMapping("/editRankLV/{wordId}")
    public ResponseEntity<Object> updateRankLV(@PathVariable("wordId") String wordId,
                                               @RequestBody Map<String, Integer> request) {
        Integer rankLV = request.get("rankLV");
        Vocabulary vocabulary = vocabularyService.updateRankLV(wordId, rankLV);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/word/{wordId}")
    public ResponseEntity<Object> deleteVocabulary(@PathVariable("wordId") String wordId) {
        vocabularyService.deleteVocabulary(wordId);
        return ResponseEntity.noContent().build();
    }
}
