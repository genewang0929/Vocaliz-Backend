package com.example.vocaliz.categoryModuleTest;

import com.example.vocaliz.categoryModule.entity.*;
import com.example.vocaliz.repository.*;
import com.example.vocaliz.userModule.entity.*;
import org.json.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryTest {
    private HttpHeaders httpHeaders;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void testCreateCategory() throws Exception {
        AppUser appUser = createUser();
        userRepository.save(appUser);

        JSONObject request = new JSONObject()
                .put("categoryName", "Folder");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/category/genewang7@gmail.com")
                .headers(httpHeaders)
                .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category.categoryId").hasJsonPath())
                .andExpect(jsonPath("$.category.categoryName").value(request.getString("categoryName")))
                .andExpect(jsonPath("$.category.vocabularies").isEmpty())
                .andExpect(jsonPath("$.category.creatorEmail").value(appUser.getEmail()))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testGetAllCategories() throws Exception {
        Category category_1 = createACategory("genewang7@gmail.com", "Folder_1");
        Category category_2 = createACategory("genewang7@gmail.com", "Folder_2");
        AppUser appUser = createUser();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/category/allCategories/genewang7@gmail.com")
                .headers(httpHeaders);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories.[0].categoryId").value(category_1.getCategoryId()))
                .andExpect(jsonPath("$.categories.[0].categoryName").value(category_1.getCategoryName()))
                .andExpect(jsonPath("$.categories.[0].vocabularies").isEmpty())
                .andExpect(jsonPath("$.categories.[0].creatorEmail").value(appUser.getEmail()))
                .andExpect(jsonPath("$.categories.[1].categoryId").value(category_2.getCategoryId()))
                .andExpect(jsonPath("$.categories.[1].categoryName").value(category_2.getCategoryName()))
                .andExpect(jsonPath("$.categories.[1].vocabularies").isEmpty())
                .andExpect(jsonPath("$.categories.[1].creatorEmail").value(appUser.getEmail()))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testGetACategory() throws Exception {
        Category category = createACategory("genewang7@gmail.com", "Folder");
        AppUser appUser = createUser();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/category/" + category.getCategoryId())
                .headers(httpHeaders);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category.categoryId").value(category.getCategoryId()))
                .andExpect(jsonPath("$.category.categoryName").value(category.getCategoryName()))
                .andExpect(jsonPath("$.category.vocabularies").isEmpty())
                .andExpect(jsonPath("$.category.creatorEmail").value(appUser.getEmail()));
    }

    @Test
    public void testRenameCategory() throws Exception {
        Category category = createACategory("genewang7@gmail.com", "Folder");
        AppUser appUser = createUser();

        JSONObject request = new JSONObject()
                .put("newCategoryName", "Folder_1");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/category/rename/" + category.getCategoryId())
                .headers(httpHeaders)
                .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category.categoryId").value(category.getCategoryId()))
                .andExpect(jsonPath("$.category.categoryName").value(request.getString("newCategoryName")))
                .andExpect(jsonPath("$.category.vocabularies").isEmpty())
                .andExpect(jsonPath("$.category.creatorEmail").value(appUser.getEmail()))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testDeleteACategory() throws Exception {
        Category category = createACategory("genewang7@gmail.com", "Folder");
        AppUser appUser = createUser();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/category/" + category.getCategoryId())
                .headers(httpHeaders);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
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

    @AfterEach
    public void clear() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}
