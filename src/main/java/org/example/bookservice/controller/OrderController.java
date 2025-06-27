package org.example.bookservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.model.Order;
import org.example.bookservice.request.OrderRequest;
import org.example.bookservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest order) {
        return new ResponseEntity<>(orderService.makeOrder(order), HttpStatus.CREATED);
    }
}
