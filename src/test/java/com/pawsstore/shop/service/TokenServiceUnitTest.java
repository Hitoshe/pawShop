package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.RefreshRequest;
import com.pawsstore.shop.dto.auth.AuthRequest;
import com.pawsstore.shop.dto.auth.AuthResponse;
import com.pawsstore.shop.dto.auth.LogoutRequest;
import com.pawsstore.shop.model.RefreshToken;
import com.pawsstore.shop.model.User;
import com.pawsstore.shop.model.roles.UserRole;
import com.pawsstore.shop.security.JwtHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TokenServiceUnitTest {

    @Mock
    private JwtHelper jwtHelper;
    @Mock
    private AuthenticationProvider provider;
    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private TokenService tokenService;


    @Test
    void login() {

        AuthRequest request = new AuthRequest("a@a.com", "1");

        User user = new User(1L, "a@a.com", "string", UserRole.CUSTOMER, LocalDateTime.now());

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("a@a.com")
                .password("1")
                .authorities(Collections.emptyList())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        Mockito.when(provider.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                        .thenReturn(authentication);

        Mockito.when(jwtHelper.createToken(user.getUsername())).thenReturn("access-token-value");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token-value");
        Mockito.when(refreshTokenService.generateToken(user.getUsername())).thenReturn(refreshToken);

        AuthResponse login = tokenService.login(request);

        Assertions.assertEquals("access-token-value", login.accessToken());
        Assertions.assertEquals("refresh-token-value", login.refreshToken());


    }

    @Test
    void refresh() {

        RefreshRequest someRefreshToken = new RefreshRequest("some refresh token");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("some new refresh token");
        User user = new User();
        user.setEmail("a@a.com");
        refreshToken.setUser(user);

        Mockito.when(refreshTokenService.rotate("some refresh token")).thenReturn(refreshToken);
        Mockito.when(jwtHelper.createToken("a@a.com")).thenReturn("new access token");

        AuthResponse refresh = tokenService.refresh(someRefreshToken);

        Assertions.assertEquals("some new refresh token", refresh.refreshToken());
        Assertions.assertEquals("new access token", refresh.accessToken());

    }

    @Test
    void revoke() {
        LogoutRequest someRefreshToken = new LogoutRequest("some refresh token");

        Mockito.when(refreshTokenService.revoke("some refresh token")).thenReturn(Boolean.TRUE);

        boolean revoke = tokenService.revoke(someRefreshToken);

        Assertions.assertTrue(revoke);
    }
}