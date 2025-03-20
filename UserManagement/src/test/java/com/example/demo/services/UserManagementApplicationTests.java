package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1, "john_doe", "securePass123", LocalDate.of(1995, 3, 10), 5000.0);
    }


    @Test
    void testRegisterUser_Success() {
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        String response = userService.registerUser(testUser);

        assertEquals("User registered successfully!", response);
        verify(userRepository, times(1)).save(testUser);
    }


    @Test
    void testRegisterUser_Failure_UsernameExists() {
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);

        String response = userService.registerUser(testUser);

        assertEquals("Username already exists!", response);
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void testVerifyUser_Success() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        boolean isValid = userService.verifyUser("john_doe", "securePass123");

        assertTrue(isValid);
    }

    @Test
    void testVerifyUser_Failure_WrongPassword() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        boolean isValid = userService.verifyUser("john_doe", "wrongPass");

        assertFalse(isValid);
    }


    @Test
    void testVerifyUser_Failure_UserNotFound() {
        when(userRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());

        boolean isValid = userService.verifyUser("unknown_user", "somePass");

        assertFalse(isValid);
    }

    @Test
    void testChangePassword_Success() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        boolean isChanged = userService.changePassword("john_doe", "securePass123", "newPass");

        assertTrue(isChanged);
        assertEquals("newPass", testUser.getPassword());
        verify(userRepository, times(1)).save(testUser);
    }


    @Test
    void testChangePassword_Failure_WrongOldPassword() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        boolean isChanged = userService.changePassword("john_doe", "wrongOldPass", "newPass");

        assertFalse(isChanged);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testChangePassword_Failure_UserNotFound() {
        when(userRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());

        boolean isChanged = userService.changePassword("unknown_user", "securePass123", "newPass");

        assertFalse(isChanged);
        verify(userRepository, never()).save(any(User.class));
    }

   
    @Test
    void testResetPassword_Success() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        boolean isReset = userService.resetPassword("john_doe", "resetPass");

        assertTrue(isReset);
        assertEquals("resetPass", testUser.getPassword());
        verify(userRepository, times(1)).save(testUser);
    }


    @Test
    void testResetPassword_Failure_UserNotFound() {
        when(userRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());

        boolean isReset = userService.resetPassword("unknown_user", "resetPass");

        assertFalse(isReset);
        verify(userRepository, never()).save(any(User.class));
    }
}
