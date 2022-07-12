package com.example.vocaliz.vocabularyModule.entity;

import lombok.*;
import org.springframework.data.annotation.*;

@Data
public class Vocabulary {
    @Id
    String vocabularyId;
    String word;
    String definition;
    Integer rankLV;  //(0~3) → default 0
}
