package org.example.bookservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.model.Book;
import org.example.bookservice.model.Order;
import org.example.bookservice.model.User;
import org.example.bookservice.repo.BookRepository;
import org.example.bookservice.repo.OrderRepository;
import org.example.bookservice.repo.UserRepository;
import org.example.bookservice.request.OrderRequest;
import org.example.bookservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    public Order makeOrder(OrderRequest orderRequest) {

        User user = userRepository.findById(orderRequest.userId())
                .orElseThrow(() -> new ItemNotFoundException("User not found"));

        Book book = bookRepository.findById(orderRequest.bookId())
                .orElseThrow(() -> new ItemNotFoundException("Book not found"));

        return orderRepository.makeOrder(user,book);
    }


}
