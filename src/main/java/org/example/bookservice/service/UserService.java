package org.example.bookservice.service;

import org.example.bookservice.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
}
