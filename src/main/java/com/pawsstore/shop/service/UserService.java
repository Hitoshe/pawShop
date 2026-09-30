package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.UserRequest;
import com.pawsstore.shop.dto.UserResponse;
import com.pawsstore.shop.exceptions.EmailAlreadyExistsException;
import com.pawsstore.shop.mapper.UserMapper;
import com.pawsstore.shop.model.User;
import com.pawsstore.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public UserResponse createUser(UserRequest userRequest, Locale locale) {

        if(userRepository.existsByEmail(userRequest.email())) {
            throw new EmailAlreadyExistsException(messageSource.getMessage("email.exists", null, locale));
        }

        User save = userRepository.save(UserMapper.toEntity(userRequest));
        return UserMapper.toResponse(save);
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return UserMapper.toResponse(user);
    }
}
