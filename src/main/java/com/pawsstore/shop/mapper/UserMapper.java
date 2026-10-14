package com.pawsstore.shop.mapper;

import com.pawsstore.shop.dto.RegistrationRequest;
import com.pawsstore.shop.dto.UserResponse;
import com.pawsstore.shop.model.User;


public final class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(RegistrationRequest request) {
        User user = new User();
        user.setEmail(request.email());
        return user;
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getRole().name(), user.getCreatedAt());
    }
}
