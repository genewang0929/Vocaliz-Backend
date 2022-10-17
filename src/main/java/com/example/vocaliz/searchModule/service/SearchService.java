package com.example.vocaliz.searchModule.service;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.categoryModule.service.*;
import com.example.vocaliz.repository.*;
import com.example.vocaliz.searchModule.entity.*;
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
    @Autowired
    private CategoryService categoryService;

    public Pages searchVocabulary(String word, String email, int offset, int pageNumber) {
        if (word.equals(""))
            return null;

        List<Vocabulary> vocabularyList = vocabularyRepository.findByWordRegex(word);
        vocabularyList = vocabularyList.stream()
                .filter((Vocabulary v) -> v.getCreatorEmail().equals(email))
                .collect(Collectors.toList());

        List<SearchReturn> searchReturnList = new ArrayList<>();
        for (Vocabulary vocabulary : vocabularyList) {
            SearchReturn searchReturn = new SearchReturn();
            searchReturn.setWord(vocabulary.getWord());
            searchReturn.setDefinition(vocabulary.getDefinition());
            Category category = categoryService.getACategory(vocabulary.getParentCategory());
            searchReturn.setParentCategoryId(category.getCategoryId());
            searchReturn.setParentCategoryName(category.getCategoryName());
            searchReturnList.add(searchReturn);
        }

        Pageable paging = PageRequest.of(offset, pageNumber);
        int start = Math.min((int)paging.getOffset(), searchReturnList.size());
        int end = Math.min((start + paging.getPageSize()), searchReturnList.size());
        Page<SearchReturn> page = new PageImpl<>(searchReturnList.subList(start, end), paging, searchReturnList.size());
        return new Pages(page.getContent(), page.getTotalPages());
    }
}
