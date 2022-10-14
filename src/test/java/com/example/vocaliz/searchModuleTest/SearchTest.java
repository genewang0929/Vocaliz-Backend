package com.example.vocaliz.searchModuleTest;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.exception.*;
import com.example.vocaliz.repository.*;
import com.example.vocaliz.userModule.entity.*;
import com.example.vocaliz.vocabularyModule.entity.*;
import org.bson.types.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchTest {
    private HttpHeaders httpHeaders;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VocabularyRepository vocabularyRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        vocabularyRepository.deleteAll();
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void testSearchVocabulary() throws Exception {
        Category category = createACategory("genewang7@gmail.com", "Folder");
        AppUser appUser = createUser();

        Vocabulary request_1 = new Vocabulary();
        request_1.setVocabularyId(new ObjectId().toString());
        request_1.setWord("continue");
        request_1.setDefinition("繼續");
        request_1.setRankLV(0);
        request_1 = createVocabulary(appUser.getEmail(), category.getCategoryId(), request_1);
        Vocabulary request_2 = new Vocabulary();
        request_2.setVocabularyId(new ObjectId().toString());
        request_2.setWord("contagious");
        request_2.setDefinition("具感染力的");
        request_2.setRankLV(1);
        request_2 = createVocabulary(appUser.getEmail(), category.getCategoryId(), request_2);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/search/genewang7@gmail.com/0/10")
                .headers(httpHeaders)
                .param("word", "cont");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vocabulary.items.[0].vocabularyId").hasJsonPath())
                .andExpect(jsonPath("$.vocabulary.items.[0].word").value(request_1.getWord()))
                .andExpect(jsonPath("$.vocabulary.items.[0].definition").value(request_1.getDefinition()))
                .andExpect(jsonPath("$.vocabulary.items.[0].rankLV").value(request_1.getRankLV()))
                .andExpect(jsonPath("$.vocabulary.items.[0].parentCategory").value(request_1.getParentCategory()))
                .andExpect(jsonPath("$.vocabulary.items.[0].creatorEmail").value(appUser.getEmail()))
                .andExpect(jsonPath("$.vocabulary.items.[1].vocabularyId").hasJsonPath())
                .andExpect(jsonPath("$.vocabulary.items.[1].word").value(request_2.getWord()))
                .andExpect(jsonPath("$.vocabulary.items.[1].definition").value(request_2.getDefinition()))
                .andExpect(jsonPath("$.vocabulary.items.[1].rankLV").value(request_2.getRankLV()))
                .andExpect(jsonPath("$.vocabulary.items.[1].parentCategory").value(request_2.getParentCategory()))
                .andExpect(jsonPath("$.vocabulary.items.[0].creatorEmail").value(appUser.getEmail()))
                .andExpect(jsonPath("$.vocabulary.totalPages").value("1"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    public AppUser createUser() {
        AppUser appUser = new AppUser();
        appUser.setEmail("genewang7@gmail.com");
        appUser.setName("Gene");
        appUser.setPassword("1234");
        appUser.setIsActivate(true);
        // 建Default資料夾
        createACategory(appUser.getEmail(), "Default");

        return userRepository.insert(appUser);
    }

    public Category createACategory(String email, String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setVocabularies(new ArrayList<>());
        category.setCreatorEmail(email);
        return categoryRepository.insert(category);
    }

    public Category getACategory(String categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Can't find category."));
    }

    public Vocabulary createVocabulary(String email, String categoryId, Vocabulary vocabulary) {
        vocabulary.setParentCategory(categoryId);
        vocabulary.setCreatorEmail(email);

        Category category = getACategory(categoryId);
        List<String> vocabularies = category.getVocabularies();
        vocabularies.add(vocabulary.getVocabularyId());
        category.setVocabularies(vocabularies);
        categoryRepository.save(category);

        return vocabularyRepository.insert(vocabulary);
    }

    @AfterEach
    public void clear() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        vocabularyRepository.deleteAll();
    }
}
