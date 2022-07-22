package com.example.vocaliz.vocabularyModule.entity;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@Document(collection = "vocabulary")
public class Vocabulary {
    @Id
    String vocabularyId;
    String word;
    String definition;
    Integer rankLV;  //(0~3) → default 0
    String parentCategory;
}
