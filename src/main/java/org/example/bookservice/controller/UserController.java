package org.example.bookservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.dto.UserDTO;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.mapper.UserMapper;
import org.example.bookservice.model.User;
import org.example.bookservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.save(UserMapper.toEntity(userDTO)), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.update(userDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
