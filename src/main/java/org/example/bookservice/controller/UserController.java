package org.example.bookservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

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

@Tag(name = "User Controller", description = "Operations related to users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponse(responseCode = "200", description = "List of users",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Create a new user", description = "Adds a new user to the system")
    @ApiResponse(responseCode = "201", description = "User created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    @PostMapping
    public ResponseEntity<User> createUser(
            @Parameter(description = "User data to create") @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.save(UserMapper.toEntity(userDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing user", description = "Updates user data by user name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping
    public ResponseEntity<User> updateUser(
            @Parameter(description = "Updated user data") @RequestBody UserDTO userDTO) {
        try {
            User user = userService.update(userDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
