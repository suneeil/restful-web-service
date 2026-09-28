package com.sun.web.restful_web_service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserJpaResourceTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    void returnsSeededUserWithNegativeId(int id) throws Exception {
        mockMvc.perform(get("/jpa/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void returnsAllUsersWithoutNestedPosts() throws Exception {
        mockMvc.perform(get("/jpa/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[*].posts").doesNotExist());
    }

    @Test
    void returnsPostsForUserWithoutNestedUser() throws Exception {
        mockMvc.perform(get("/jpa/users/-1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(-1))
                .andExpect(jsonPath("$[0].description").value("Post by Jack"))
                .andExpect(jsonPath("$[*].user").doesNotExist());
    }

    @Test
    void returnsNotFoundForPostsOfUnknownUser() throws Exception {
        mockMvc.perform(get("/jpa/users/-999/posts"))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnsNotFoundForUnknownUser() throws Exception {
        mockMvc.perform(get("/jpa/users/-999"))
                .andExpect(status().isNotFound());
    }
}
