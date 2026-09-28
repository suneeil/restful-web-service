package com.sun.web.restful_web_service.versioning;

import com.sun.web.restful_web_service.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VersioningPersonControllerTests {

    @Test
    void returnsFirstVersionOfPersonWithoutQueryParameters() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new VersioningPersonController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mockMvc.perform(get("/v1/person").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"Bob Charlie\"}"));
    }
}
