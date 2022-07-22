package com.example.vocaliz.quizModule.service;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.categoryModule.service.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import com.example.vocaliz.vocabularyModule.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class QuizService {
    @Autowired
    private VocabularyService vocabularyService;
    public List<Vocabulary> getQuizVocabularies(String categoryId, String rankLV, String wordNum) {
        List<Vocabulary> vocabularyList;
        if (rankLV.equals(""))    // 無指定LV -> 所有單字
            vocabularyList = vocabularyService.getAllVocabulariesInCategory(categoryId);
        else {
            int rank = Integer.parseInt(rankLV);
            vocabularyList = vocabularyService.getVocabulariesByRankLVInCategory(categoryId, rank);
        }

        Collections.shuffle(vocabularyList);
        if (wordNum.equals("10") && vocabularyList.size() >= 10)
            return vocabularyList.subList(0, 10);
        if (wordNum.equals("20") && vocabularyList.size() >= 20)
            return vocabularyList.subList(0, 20);
        if (wordNum.equals("30") && vocabularyList.size() >= 30)
            return vocabularyList.subList(0, 30);
        else    // 沒指定quiz單字數 || quiz單字數未達指定數量
            return vocabularyList;
    }

}
