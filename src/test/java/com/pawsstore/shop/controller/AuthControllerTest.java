package com.pawsstore.shop.controller;

import com.pawsstore.shop.dto.RefreshRequest;
import com.pawsstore.shop.dto.auth.AuthRequest;
import com.pawsstore.shop.dto.auth.AuthResponse;
import com.pawsstore.shop.dto.errors.ErrorResponse;
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
    @Sql(scripts = {"/data/deleteData.sql", "/data/insertData.sql"})
    void loginNegative() throws Exception {

        AuthRequest authRequest = new AuthRequest("a1@a.com", "12");

        String requestJson = objectMapper.writeValueAsString(authRequest);

        String exception = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ErrorResponse errorResponse = objectMapper.readValue(exception, ErrorResponse.class);

        Assertions.assertEquals("Bad credentials", errorResponse.message());
    }

    @Test
    @Sql({"/data/deleteData.sql", "/data/insertData.sql", "/data/insertRefreshToken.sql"})
    void refreshAccessToken() throws Exception {
        RefreshRequest refreshRequest = new RefreshRequest("36bc87ec-3461-47da-918e-e0d9a7b705b9");

        String s = objectMapper.writeValueAsString(refreshRequest);
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(contentAsString, AuthResponse.class);

        Assertions.assertTrue(jwtHelper.validateToken(authResponse.accessToken(), "a@a.com"));

    }

    @Test
    void logout() {
    }
}