package com.loanmanagement;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.model.User;
import com.loanmanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserById_ValidId_ReturnsUser() {
        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setUsername("testuser");

        when(userDaoMock.getUserById(anyInt())).thenReturn(mockUser);

        User result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }
}