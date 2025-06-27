package org.example.bookservice.service;

import org.example.bookservice.dto.UserDTO;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.model.User;

import java.util.List;
public interface UserService {
    List<UserDTO> findAll();
    User save(User user);
    void delete(Long id) throws ItemNotFoundException;
    User update(UserDTO userDTO) throws ItemNotFoundException;
}
