package com.example.vocaliz.vocabularyModule.service;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.categoryModule.service.*;
import com.example.vocaliz.repository.*;
import com.example.vocaliz.userModule.entity.*;
import com.example.vocaliz.userModule.service.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import org.bson.types.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class VocabularyService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private UserRepository userRepository;

    public Vocabulary getAVocabulary(String email, String categoryId, String wordId) {
        Category category = categoryService.getACategory(email, categoryId);
        List<Vocabulary> vocabularyList = category.getVocabulary();
        for (Vocabulary vocabulary : vocabularyList) {
            if (vocabulary.getVocabularyId().equals(wordId))
                return vocabulary;
        }
        return null;
    }

    public Pages getAllVocabularies(String email, String categoryId, int offset, int pageNumber) {
        Category category = categoryService.getACategory(email, categoryId);
        List<Vocabulary> vocabularyList = category.getVocabulary();

        Pageable paging = PageRequest.of(offset, pageNumber, Sort.by("word").descending());
        int start = Math.min((int)paging.getOffset(), vocabularyList.size());
        int end = Math.min((start + paging.getPageSize()), vocabularyList.size());
        Page<Vocabulary> page = new PageImpl<>(vocabularyList.subList(start, end), paging, vocabularyList.size());

        return new Pages(page.getContent(), page.getTotalPages());
    }

    public List<Vocabulary> getAllVocabularies(String email, String categoryId) {
        Category category = categoryService.getACategory(email, categoryId);
        return category.getVocabulary();
    }

    public Pages getVocabulariesByRankLV(String email, String categoryId, int rankLV, int offset, int pageNumber) {
        Category category = categoryService.getACategory(email, categoryId);
        List<Vocabulary> vocabularyListLV = category.getVocabulary();
        vocabularyListLV = vocabularyListLV.stream()
                .filter((Vocabulary v) -> v.getRankLV() == rankLV)
                .collect(Collectors.toList());

        Pageable paging = PageRequest.of(offset, pageNumber, Sort.by("word").descending());
        int start = Math.min((int)paging.getOffset(), vocabularyListLV.size());
        int end = Math.min((start + paging.getPageSize()), vocabularyListLV.size());
        Page<Vocabulary> page = new PageImpl<>(vocabularyListLV.subList(start, end), paging, vocabularyListLV.size());

        return new Pages(page.getContent(), page.getTotalPages());
    }

    public List<Vocabulary> getVocabulariesByRankLV(String email, String categoryId, int rankLV) {
        Category category = categoryService.getACategory(email, categoryId);
        List<Vocabulary> vocabularyListLV = category.getVocabulary();
        return vocabularyListLV.stream()
                .filter((Vocabulary v) -> v.getRankLV() == rankLV)
                .collect(Collectors.toList());
    }

    public Vocabulary createVocabulary(String email, String categoryId, Vocabulary request) {
        System.out.println(request.getWord());
        System.out.println(request.getDefinition());


        AppUser appUser = appUserService.getUserByEmail(email);
        List<Category> categories = appUser.getCategory();
        Category category = new Category();
        for (Category c : categories) {
            if (c.getCategoryId().equals(categoryId))
                category = c;
        }
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setVocabularyId(new ObjectId().toString());
        vocabulary.setWord(request.getWord());
        vocabulary.setDefinition(request.getDefinition());
        vocabulary.setRankLV(0);
        //新增至指定Category
        List<Vocabulary> vocabularyList = category.getVocabulary();
        vocabularyList.add(vocabulary);
        category.setVocabulary(vocabularyList);
        //新增至Default Category
        Category defaultCategory = appUser.getCategory().get(0);
        List<Vocabulary> defaultVocabularyList = defaultCategory.getVocabulary();
        defaultVocabularyList.add(vocabulary);
        defaultCategory.setVocabulary(defaultVocabularyList);

        userRepository.save(appUser);
        return vocabulary;
    }

    public Vocabulary updateVocabulary(String email, String categoryId, String wordId, Vocabulary request) {
        AppUser appUser = appUserService.getUserByEmail(email);
        Category category = categoryService.getACategory(email, categoryId);
        List<Vocabulary> vocabularyList = category.getVocabulary();
        Vocabulary vocabulary;
        for (int i = 0; i < vocabularyList.size(); i++) {
            vocabulary = vocabularyList.get(i);
            if (vocabulary.getVocabularyId().equals(wordId)) {
                vocabulary.setWord(request.getWord());
                vocabulary.setDefinition(request.getDefinition());
                vocabularyList.set(i, vocabulary);
                category.setVocabulary(vocabularyList);
                userRepository.save(appUser);
                return vocabulary;
            }
        }
        return null;
    }

    public List<Vocabulary> deleteVocabulary(String email, String categoryId, String wordId) {
        AppUser appUser = appUserService.getUserByEmail(email);
        Category category = categoryService.getACategory(email, categoryId);
        List<Vocabulary> vocabularyList = category.getVocabulary();
        Vocabulary vocabulary = getAVocabulary(email, categoryId, wordId);
        vocabularyList.remove(vocabulary);
        category.setVocabulary(vocabularyList);
        userRepository.save(appUser);
        return vocabularyList;
    }

    public List<Vocabulary> deleteAllVocabularies(String email, String categoryId) {
        AppUser appUser = appUserService.getUserByEmail(email);
        Category category = categoryService.getACategory(email, categoryId);
        List<Vocabulary> vocabularyList = category.getVocabulary();
        vocabularyList.clear();
        category.setVocabulary(vocabularyList);
        userRepository.save(appUser);
        return vocabularyList;
    }

    public Vocabulary updateRankLV(String email, String categoryId, String wordId, int rankLV) {
        AppUser appUser = appUserService.getUserByEmail(email);
        Category category = categoryService.getACategory(email, categoryId);
        List<Vocabulary> vocabularyList = category.getVocabulary();
        Vocabulary vocabulary;
        for (int i = 0; i < vocabularyList.size(); i++) {
            vocabulary = vocabularyList.get(i);
            if (vocabulary.getVocabularyId().equals(wordId)) {
                vocabulary.setRankLV(rankLV);
                vocabularyList.set(i, vocabulary);
                category.setVocabulary(vocabularyList);
                userRepository.save(appUser);
                return vocabulary;
            }
        }
        return null;
    }
}
