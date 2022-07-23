package com.example.vocaliz.searchModule.controller;

import com.example.vocaliz.searchModule.service.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/{email}/{offset}/{pageNumber}")
    public ResponseEntity<Object> searchVocabulary(@PathVariable("email") String email,
                                                   @PathVariable("offset") int offset,
                                                   @PathVariable("pageNumber") int pageNumber,
                                                   @RequestParam(value = "word", defaultValue = "") String word) {
        Pages vocabularyList = searchService.searchVocabulary(word, email, offset, pageNumber);
        Map<String, Object> map = new HashMap<>();
        map.put("vocabulary", Objects.requireNonNullElse(vocabularyList, "word not found"));
        return ResponseEntity.ok(map);
    }

}
