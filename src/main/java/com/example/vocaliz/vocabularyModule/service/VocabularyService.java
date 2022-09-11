package com.example.vocaliz.vocabularyModule.service;

//import com.example.vocaliz.categoryModule.entity.*;
//import com.example.vocaliz.categoryModule.service.*;
//import com.example.vocaliz.repository.*;
//import com.example.vocaliz.userModule.entity.*;
import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.categoryModule.service.*;
import com.example.vocaliz.exception.*;
import com.example.vocaliz.repository.*;
import com.example.vocaliz.userModule.service.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import org.bson.types.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class VocabularyService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private VocabularyRepository vocabularyRepository;

    public Vocabulary getAVocabulary(String wordId) {
        return vocabularyRepository.findById(wordId)
                .orElseThrow(() -> new NotFoundException("Can't find vocabulary."));
    }

    public Pages getAllVocabulariesInCategory(String categoryId, int offset, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(offset, pageNumber);
        Page<Vocabulary> vocabularies = vocabularyRepository.findAllByParentCategory(categoryId, pageRequest);
        return new Pages(vocabularies.getContent(), vocabularies.getTotalPages());
    }

    public List<Vocabulary> getAllVocabulariesInCategory(String categoryId) {
        return vocabularyRepository.findAllByParentCategory(categoryId);
    }

    public Pages getVocabulariesByRankLVInCategory(String categoryId, int rankLV, int offset, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(offset, pageNumber);
        Page<Vocabulary> vocabularies = vocabularyRepository.findAllByRankLVAndParentCategory(rankLV, categoryId, pageRequest);
        return new Pages(vocabularies.getContent(), vocabularies.getTotalPages());
    }

    public List<Vocabulary> getVocabulariesByRankLVInCategory(String categoryId, int rankLV) {
        return vocabularyRepository.findAllByRankLVAndParentCategory(rankLV, categoryId);
    }

    public Vocabulary createVocabulary(String email, String categoryId, Vocabulary request) {
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setVocabularyId(new ObjectId().toString());
        vocabulary.setWord(request.getWord());
        vocabulary.setDefinition(request.getDefinition());
        vocabulary.setRankLV(0);
        vocabulary.setParentCategory(categoryId);
        vocabulary.setCreatorEmail(email);

        Category category = categoryService.getACategory(categoryId);
        List<String> vocabularies = category.getVocabularies();
        vocabularies.add(vocabulary.getVocabularyId());
        category.setVocabularies(vocabularies);
        categoryRepository.save(category);

        return vocabularyRepository.insert(vocabulary);
    }

    public Vocabulary updateVocabulary(String wordId, Vocabulary request) {
        Vocabulary vocabulary = getAVocabulary(wordId);
        vocabulary.setWord(request.getWord());
        vocabulary.setDefinition(request.getDefinition());
        return vocabularyRepository.save(vocabulary);
    }

    public Vocabulary updateRankLV(String wordId, int rankLV) {
        Vocabulary vocabulary = getAVocabulary(wordId);
        vocabulary.setRankLV(rankLV);
        return vocabularyRepository.save(vocabulary);
    }

    public void deleteVocabulary(String wordId) {
        Vocabulary vocabulary = getAVocabulary(wordId);
        String categoryId = vocabulary.getParentCategory();
        Category category = categoryService.getACategory(categoryId);
        category.getVocabularies().removeIf(vocabularyId -> vocabularyId.equals(wordId));
        categoryRepository.save(category);
        vocabularyRepository.deleteById(wordId);
    }

}
