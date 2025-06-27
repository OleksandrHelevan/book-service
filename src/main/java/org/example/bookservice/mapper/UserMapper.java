package org.example.bookservice.mapper;

import org.example.bookservice.dto.UserDTO;
import org.example.bookservice.model.User;

public class UserMapper {

    public static UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        return user;
    }
}
