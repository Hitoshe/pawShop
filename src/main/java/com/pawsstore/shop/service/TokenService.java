package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.RefreshRequest;
import com.pawsstore.shop.dto.auth.AuthRequest;
import com.pawsstore.shop.dto.auth.AuthResponse;
import com.pawsstore.shop.dto.auth.LogoutRequest;
import com.pawsstore.shop.model.RefreshToken;
import com.pawsstore.shop.security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtHelper jwtHelper;
    private final AuthenticationProvider provider;
    private final RefreshTokenService refreshTokenService;


    public AuthResponse login(AuthRequest request) {
        Authentication authenticate =
                provider.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UserDetails principal = (UserDetails) authenticate.getPrincipal();

        String accessToken = jwtHelper.createToken(principal.getUsername());
        RefreshToken refreshToken = refreshTokenService.generateToken(principal.getUsername());

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    public AuthResponse refresh(RefreshRequest request) {
        RefreshToken newRefreshToken = refreshTokenService.rotate(request.refreshToken());
        String newAccessToken = jwtHelper.createToken(newRefreshToken.getUser().getEmail());
        return new AuthResponse(newAccessToken, newRefreshToken.getToken());
    }

    public boolean revoke(LogoutRequest request) {
        return refreshTokenService.revoke(request.refreshToken());
    }
}
