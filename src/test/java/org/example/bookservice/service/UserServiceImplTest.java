package org.example.bookservice.service;

import org.example.bookservice.dto.UserDTO;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.model.User;
import org.example.bookservice.repo.UserRepository;
import org.example.bookservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "John", null);
        userDTO = new UserDTO("John");
    }

    @Test
    void findAll_ShouldReturnListOfUserDTOs() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> result = userService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("John", result.getFirst().getName());

        verify(userRepository).findAll();
    }

    @Test
    void save_ShouldSaveUser() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals(user.getName(), savedUser.getName());

        verify(userRepository).save(user);
    }

    @Test
    void update_ShouldUpdateExistingUser() throws ItemNotFoundException {
        when(userRepository.findByName(userDTO.getName())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.update(userDTO);

        assertNotNull(updatedUser);
        assertEquals(user.getName(), updatedUser.getName());

        verify(userRepository).findByName(userDTO.getName());
        verify(userRepository).save(user);
    }

    @Test
    void update_ShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByName(userDTO.getName())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> userService.update(userDTO));

        verify(userRepository).findByName(userDTO.getName());
        verify(userRepository, never()).save(any());
    }

    @Test
    void delete_ShouldDeleteUserWhenExists() throws ItemNotFoundException {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);

        userService.delete(id);

        verify(userRepository).existsById(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    void delete_ShouldThrowExceptionWhenUserNotFound() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> userService.delete(id));

        verify(userRepository).existsById(id);
        verify(userRepository, never()).deleteById(any());
    }
}
