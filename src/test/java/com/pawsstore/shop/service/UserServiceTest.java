package com.pawsstore.shop.service;

import com.pawsstore.shop.dto.RegistrationRequest;
import com.pawsstore.shop.dto.UserResponse;
import com.pawsstore.shop.model.User;
import com.pawsstore.shop.model.roles.UserRole;
import com.pawsstore.shop.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private MessageSource messageSource;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser() {
        Locale english = Locale.ENGLISH;
        RegistrationRequest registrationRequest = new RegistrationRequest("a@a.com", "1");

        Mockito.when(userRepository.existsByEmail("a@a.com")).thenReturn(false);
        Mockito.when(passwordEncoder.encode("1")).thenReturn("$2a$10$Pq0EK5B.g7SaftmjjIiZIOw/l2sMxNYZ9p2HGTAoEfuCPpX4hWEJ2");

        Mockito.when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.createUser(registrationRequest, english);

        Assertions.assertEquals("a@a.com", response.email());
        Assertions.assertEquals("CUSTOMER", response.role());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        Mockito.verify(userRepository).save(userArgumentCaptor.capture());

        User capture = userArgumentCaptor.getValue();

        Assertions.assertEquals("a@a.com" ,capture.getEmail());
        Assertions.assertEquals(UserRole.CUSTOMER, capture.getRole());
        Assertions.assertEquals("$2a$10$Pq0EK5B.g7SaftmjjIiZIOw/l2sMxNYZ9p2HGTAoEfuCPpX4hWEJ2", capture.getPasswordHash());
    }
}