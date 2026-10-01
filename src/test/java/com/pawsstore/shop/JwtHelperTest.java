package com.pawsstore.shop;

import com.pawsstore.shop.model.User;
import com.pawsstore.shop.model.roles.UserRole;
import com.pawsstore.shop.security.JwtHelper;
import com.pawsstore.shop.security.JwtProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtHelperTest")
public class JwtHelperTest {

    private static final String SECRET_KEY =
            "dGhpcy1pcy1hLXZlcnktbG9uZy1zZWNyZXQta2V5LWZvci1qd3Qtc2lnbmluZy0xMjM0";
    private static final long EXPIRATION = 3_600_000L;

    private JwtHelper jwtHelper;
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
        JwtProperties properties = new JwtProperties(EXPIRATION, SECRET_KEY);
        jwtHelper = new JwtHelper(properties);
        userDetails = new User(1L, "a@a.com", "passwordHash", UserRole.CUSTOMER, LocalDateTime.now());
    }

    @Test
    void shouldGenerateToken() {
        String token = jwtHelper.createToken(new HashMap<>(), userDetails.getUsername());

        System.out.println(token);

        assertThat(token).isNotBlank();
        assertThat(token.split("\\.")).hasSize(3);

    }

    @Test
    void shouldExtractData() {
        String token = jwtHelper.createToken(new HashMap<>(), userDetails.getUsername());

        System.out.println(jwtHelper.extractExpirationTime(token));

        assertThat(jwtHelper.extractExpirationTime(token)).isNotNull();
    }

    @Test
    void shouldValidateToken() {
        String token = jwtHelper.createToken(new HashMap<>(), userDetails.getUsername());
        assertThat(jwtHelper.validateToken(token, "a@a.com")).isTrue();
    }

    @Test
    void shouldCorrectlyExtractUsername() {
        String token = jwtHelper.createToken(new HashMap<>(), userDetails.getUsername());
        assertThat(jwtHelper.extractUsername(token)).isEqualTo("a@a.com");

    }

    @Test
    void shouldRejectExpiredToken() {
        JwtProperties properties = new JwtProperties(-1000000L, SECRET_KEY);
        JwtHelper expired = new JwtHelper(properties);
        String token = expired.createToken(new HashMap<>(), userDetails.getUsername());

        assertThat(expired.validateToken(token, "a@a.com")).isFalse();
    }
}
