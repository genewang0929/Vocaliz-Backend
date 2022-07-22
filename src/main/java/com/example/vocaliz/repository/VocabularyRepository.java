package com.example.vocaliz.repository;

import com.example.vocaliz.vocabularyModule.entity.*;
import org.springframework.data.mongodb.repository.*;

public interface VocabularyRepository extends MongoRepository<Vocabulary, String> {

}
