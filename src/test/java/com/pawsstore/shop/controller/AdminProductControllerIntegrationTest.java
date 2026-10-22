package com.pawsstore.shop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"/data/deleteData.sql", "/data/insertData.sql", "/data/insertAdmin.sql"})
@Sql(scripts = "/data/deleteData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AdminProductControllerIntegrationTest {

    private static final String PRODUCT = """
            {
              "title": "АдминКорм",
              "description": "Корм, который завёл администратор",
              "price": 50.00,
              "rating": 4.5,
              "images": ["https://img/1.png", "https://img/2.png", "https://img/3.png"],
              "stockQuantity": 8
            }
            """;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void customerCannotCreateProduct() throws Exception {
        mockMvc.perform(post("/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PRODUCT))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/api/admin/products")
                        .header("Authorization", "Bearer " + login("a@a.com", "1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PRODUCT))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanCreateUpdateAndSoftDeleteProduct() throws Exception {
        String admin = login("admin@a.com", "1");

        MvcResult created = mockMvc.perform(post("/api/admin/products")
                        .header("Authorization", "Bearer " + admin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PRODUCT))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("АдминКорм"))
                .andExpect(jsonPath("$.stockQuantity").value(8))
                .andExpect(jsonPath("$.images.length()").value(3))
                .andReturn();
        long productId = objectMapper.readTree(created.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(get("/api/catalog").param("search", "АдминКорм"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.elements.length()").value(1));

        mockMvc.perform(patch("/api/admin/products/" + productId)
                        .header("Authorization", "Bearer " + admin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"price": 80.00}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(80.00))
                .andExpect(jsonPath("$.title").value("АдминКорм"));

        mockMvc.perform(delete("/api/admin/products/" + productId)
                        .header("Authorization", "Bearer " + login("a@a.com", "1")))
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/api/admin/products/" + productId)
                        .header("Authorization", "Bearer " + admin))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/catalog").param("search", "АдминКорм"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.elements.length()").value(0));

        mockMvc.perform(delete("/api/admin/products/" + productId)
                        .header("Authorization", "Bearer " + admin))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/api/cart/items")
                        .header("Authorization", "Bearer " + login("a@a.com", "1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"productId": %d, "quantity": 1}
                                """.formatted(productId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PRODUCT_NOT_FOUND"));
    }

    private String login(String email, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"%s","password":"%s"}
                                """.formatted(email, password)))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString()).get("accessToken").asText();
    }
}
