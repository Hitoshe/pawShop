package com.pawsstore.shop.controller;

import com.pawsstore.shop.dto.auth.AuthRequest;
import com.pawsstore.shop.dto.auth.AuthResponse;
import com.pawsstore.shop.security.JwtHelper;
import com.pawsstore.shop.service.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = {"/data/deleteData.sql", "/data/insertData.sql"})
    void login() throws Exception {

        AuthRequest authRequest = new AuthRequest("a@a.com", "1");

        String requestJson = objectMapper.writeValueAsString(authRequest);

        String tokens = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(tokens, AuthResponse.class);

        Assertions.assertEquals(authRequest.email(), jwtHelper.extractUsername(authResponse.accessToken()));
    }

    @Test
    @Sql(scripts = {"data/deleteData.sql", "data/insertData.sql"})
    void loginNegative() {
    }

    @Test
    void refreshAccessToken() {
    }

    @Test
    void logout() {
    }
}