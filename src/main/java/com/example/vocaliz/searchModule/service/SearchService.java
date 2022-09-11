package com.example.vocaliz.searchModule.service;

import com.example.vocaliz.repository.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class SearchService {
    @Autowired
    private VocabularyRepository vocabularyRepository;

    public Pages searchVocabulary(String word, String email, int offset, int pageNumber) {
        if (word.equals(""))
            return null;

        List<Vocabulary> vocabularyList = vocabularyRepository.findByWordRegex(word);
        vocabularyList = vocabularyList.stream()
                .filter((Vocabulary v) -> v.getCreatorEmail().equals(email))
                .collect(Collectors.toList());

        Pageable paging = PageRequest.of(offset, pageNumber);
        int start = Math.min((int)paging.getOffset(), vocabularyList.size());
        int end = Math.min((start + paging.getPageSize()), vocabularyList.size());
        Page<Vocabulary> page = new PageImpl<>(vocabularyList.subList(start, end), paging, vocabularyList.size());
        return new Pages(page.getContent(), page.getTotalPages());
    }
}
