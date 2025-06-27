package org.example.bookservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.dto.UserDTO;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.mapper.UserMapper;
import org.example.bookservice.model.User;
import org.example.bookservice.repo.UserRepository;
import org.example.bookservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(UserDTO userDTO) throws ItemNotFoundException{
        User user = userRepository.findByName(userDTO.getName())
                .orElseThrow(() -> new ItemNotFoundException(userDTO.getName()));
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) throws ItemNotFoundException {
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
        else throw new ItemNotFoundException("User not found");
    }

}
