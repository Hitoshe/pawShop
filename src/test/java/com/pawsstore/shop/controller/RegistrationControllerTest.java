package com.pawsstore.shop.controller;

import com.pawsstore.shop.dto.RegistrationRequest;
import com.pawsstore.shop.dto.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registration() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest("a@a.com", "1");

        String request = objectMapper.writeValueAsString(registrationRequest);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserResponse userResponse = objectMapper.readValue(result, UserResponse.class);

        Assertions.assertEquals(registrationRequest.email(), userResponse.email());
    }
}