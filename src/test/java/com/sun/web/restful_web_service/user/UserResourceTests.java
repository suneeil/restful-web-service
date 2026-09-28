package com.sun.web.restful_web_service.user;

import com.sun.web.restful_web_service.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserResourceTests {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserResource(new UserDaoService()))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void returnsBadRequestForNonNumericUserId() throws Exception {
        mockMvc.perform(get("/users/sam"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Invalid request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("Invalid value for parameter 'id'"))
                .andExpect(jsonPath("$.instance").value("/users/sam"))
                .andExpect(jsonPath("$.timestamp").isString());
    }

    @Test
    void returnsProblemDetailForUnknownUser() throws Exception {
        mockMvc.perform(get("/users/30"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("User not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("User with id 30 was not found"))
                .andExpect(jsonPath("$.instance").value("/users/30"));
    }

    @Test
    void returnsCreatedUserWithGeneratedId() throws Exception {
        int expectedId = new UserDaoService().findAll().stream()
                .mapToInt(User::getId).max().orElse(0) + 1;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"user_name":"Alice","birthDate":"2000-01-15"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedId))
                .andExpect(jsonPath("$.user_name").value("Alice"))
                .andExpect(jsonPath("$.birthDate").value("2000-01-15"));

        mockMvc.perform(get("/users/{id}", expectedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedId))
                .andExpect(jsonPath("$.user_name").value("Alice"));
    }

    @Test
    void returnsUsersWithRenamedNameProperty() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user_name").value("Adam"))
                .andExpect(jsonPath("$[0].name").doesNotExist());
    }

    @Test
    void returnsExistingUser() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.user_name").value("Adam"))
                .andExpect(jsonPath("$.name").doesNotExist());
    }
}
