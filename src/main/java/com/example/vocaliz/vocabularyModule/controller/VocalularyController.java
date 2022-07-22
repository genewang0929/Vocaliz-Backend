package com.example.vocaliz.vocabularyModule.controller;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.categoryModule.service.*;
import com.example.vocaliz.userModule.entity.*;
import com.example.vocaliz.userModule.service.*;
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

    @GetMapping("/word/{email}/{categoryId}/{wordId}")
    public ResponseEntity<Object> getAVocabulary(@PathVariable("email") String email,
                                                 @PathVariable("categoryId") String categoryId,
                                                 @PathVariable("wordId") String wordId) {
        Vocabulary vocabulary = vocabularyService.getAVocabulary(email, categoryId, wordId);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/allWords/{email}/{categoryId}/{offset}/{pageNumber}")
    public ResponseEntity<Object> getAllVocabularies(@PathVariable("email") String email,
                                                     @PathVariable("categoryId") String categoryId,
                                                     @PathVariable("offset") int offset,
                                                     @PathVariable("pageNumber") int pageNumber) {
        Pages vocabulary = vocabularyService.getAllVocabularies(email, categoryId, offset, pageNumber);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/rankLVWords/{email}/{categoryId}/{rankLV}/{offset}/{pageNumber}")
    public ResponseEntity<Object> getVocabulariesByRankLV(@PathVariable("email") String email,
                                                          @PathVariable("categoryId") String categoryId,
                                                          @PathVariable("rankLV") int rankLV,
                                                          @PathVariable("offset") int offset,
                                                          @PathVariable("pageNumber") int pageNumber) {
        Pages vocabulary = vocabularyService.getVocabulariesByRankLV(email, categoryId, rankLV, offset, pageNumber);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/word/{email}/{categoryId}")
    public ResponseEntity<Object> createAVocabulary(@PathVariable("email") String email,
                                                    @PathVariable("categoryId") String categoryId,
                                                    @RequestBody Vocabulary request) {
        Vocabulary vocabulary = vocabularyService.createVocabulary(email, categoryId, request);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", vocabulary);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/word/{email}/{categoryId}/{wordId}")
    public ResponseEntity<Object> updateVocabulary(@PathVariable("email") String email,
                                                   @PathVariable("categoryId") String categoryId,
                                                   @PathVariable("wordId") String wordId,
                                                   @RequestBody Vocabulary request) {
        Vocabulary vocabulary = vocabularyService.updateVocabulary(email, categoryId, wordId, request);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/word/{email}/{categoryId}/{wordId}")
    public ResponseEntity<Object> deleteVocabulary(@PathVariable("email") String email,
                                                   @PathVariable("categoryId") String categoryId,
                                                   @PathVariable("wordId") String wordId) {
        List<Vocabulary> vocabularyList = vocabularyService.deleteVocabulary(email, categoryId, wordId);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabularyList", vocabularyList);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/word/{email}/{categoryId}")
    public ResponseEntity<Object> deleteAllVocabularies(@PathVariable("email") String email,
                                                   @PathVariable("categoryId") String categoryId) {
        List<Vocabulary> vocabularyList = vocabularyService.deleteAllVocabularies(email, categoryId);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabularyList", vocabularyList);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/editRankLV/{email}/{categoryId}/{wordId}")
    public ResponseEntity<Object> updateRankLV(@PathVariable("email") String email,
                                               @PathVariable("categoryId") String categoryId,
                                               @PathVariable("wordId") String wordId,
                                               @RequestBody Map<String, Integer> request) {
        Integer rankLV = request.get("rankLV");
        Vocabulary vocabulary = vocabularyService.updateRankLV(email, categoryId, wordId, rankLV);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabulary, "word not found"));
        return ResponseEntity.ok(map);
    }
}
