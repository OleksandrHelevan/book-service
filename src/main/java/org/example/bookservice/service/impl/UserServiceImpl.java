package org.example.bookservice.service.impl;

import org.example.bookservice.model.User;
import org.example.bookservice.repo.UserRepository;
import org.example.bookservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
