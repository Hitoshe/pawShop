package com.pawsstore.shop.service;

import com.pawsstore.shop.exceptions.ExpiredRefreshTokenException;
import com.pawsstore.shop.exceptions.RefreshTokenInvalidException;
import com.pawsstore.shop.exceptions.RefreshTokenRevokedException;
import com.pawsstore.shop.exceptions.UserNotFoundException;
import com.pawsstore.shop.model.RefreshToken;
import com.pawsstore.shop.model.User;
import com.pawsstore.shop.repository.RefreshTokenRepository;
import com.pawsstore.shop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final long refreshDurationDays = 7L;

    private final RefreshTokenRepository repository;
    private final UserRepository userRepository;

    @Transactional
    public RefreshToken generateToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("Пользователь не найден"));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(refreshDurationDays, ChronoUnit.DAYS));

        return repository.save(refreshToken);
    }

    @Transactional(dontRollbackOn = ExpiredRefreshTokenException.class)
    public RefreshToken rotate(String token) {
        RefreshToken refreshToken = repository.findByToken(token).orElseThrow(()
                -> new RefreshTokenInvalidException("Токена нет в базе"));

        if(refreshToken.isRevoked()) {
            throw new RefreshTokenRevokedException("Рефреш токен отозван");
        }

        if(refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(refreshToken);
            throw new ExpiredRefreshTokenException("Рефреш токен все... Только перелогин");
        }

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(refreshDurationDays, ChronoUnit.DAYS));

        return repository.save(refreshToken);
    }

    @Transactional
    public boolean revoke(String token) {
        RefreshToken refreshToken = repository.findByToken(token).orElseThrow(()
                -> new RefreshTokenInvalidException("Токена нет в базе"));
        refreshToken.setRevoked(true);
        return true;
    }
}
