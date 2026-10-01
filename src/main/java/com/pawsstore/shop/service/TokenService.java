package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.TokenRequest;
import com.pawsstore.shop.dto.TokenResponse;
import com.pawsstore.shop.security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtHelper jwtHelper;
    private final AuthenticationProvider provider;


    public TokenResponse login(TokenRequest request) {
        Authentication authenticate =
                provider.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UserDetails principal = (UserDetails) authenticate.getPrincipal();

        String token = jwtHelper.createToken(new HashMap<>(), principal.getUsername());

        return new TokenResponse(token);
    }
}
