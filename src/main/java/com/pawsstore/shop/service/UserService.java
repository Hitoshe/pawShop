package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.UserRequest;
import com.pawsstore.shop.dto.UserResponse;
import com.pawsstore.shop.mapper.UserMapper;
import com.pawsstore.shop.model.User;
import com.pawsstore.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        User save = userRepository.save(UserMapper.toEntity(userRequest));
        return UserMapper.toResponse(save);
    }

    public UserResponse getUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return UserMapper.toResponse(user);
    }
}
