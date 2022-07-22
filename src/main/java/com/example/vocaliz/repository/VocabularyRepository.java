package com.example.vocaliz.repository;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.*;

import java.util.*;

public interface VocabularyRepository extends MongoRepository<Vocabulary, String> {
    List<Vocabulary> findAllByParentCategory(String categoryId);
    Page<Vocabulary> findAllByParentCategory(String categoryId, Pageable pageable);
    List<Vocabulary> findAllByRankLVAndParentCategory(int rankLV, String categoryId);
    Page<Vocabulary> findAllByRankLVAndParentCategory(int rankLV, String categoryId, Pageable pageable);
}
