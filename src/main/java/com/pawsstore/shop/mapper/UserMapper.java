package com.pawsstore.shop.mapper;

import com.pawsstore.shop.dto.UserRequest;
import com.pawsstore.shop.dto.UserResponse;
import com.pawsstore.shop.model.User;
import com.pawsstore.shop.model.roles.UserRole;


public final class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(UserRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setRole(UserRole.valueOf(request.role()));
        user.setPasswordHash(request.password());
        return user;
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getRole().name(), user.getCreatedAt());
    }
}
