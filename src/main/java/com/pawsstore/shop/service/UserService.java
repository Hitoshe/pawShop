package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.RegistrationRequest;
import com.pawsstore.shop.dto.UserResponse;
import com.pawsstore.shop.exceptions.EmailAlreadyExistsException;
import com.pawsstore.shop.exceptions.UserNotFoundException;
import com.pawsstore.shop.mapper.UserMapper;
import com.pawsstore.shop.model.User;
import com.pawsstore.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(RegistrationRequest userRequest, Locale locale) {

        if(userRepository.existsByEmail(userRequest.email())) {
            throw new EmailAlreadyExistsException(messageSource.getMessage("email.exists", null, locale));
        }

        User entity = UserMapper.toEntity(userRequest);

        entity.setPasswordHash(passwordEncoder.encode(userRequest.password()));

        User save = userRepository.save(entity);
        return UserMapper.toResponse(save);
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("Пользователь не найден"));
        return UserMapper.toResponse(user);
    }

    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return UserMapper.toResponse(user);
    }
}
