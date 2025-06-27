package org.example.bookservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.exception.AmountIsZeroException;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.model.Book;
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
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            Order order = orderService.makeOrder(orderRequest);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AmountIsZeroException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books/{user_id}")
    public ResponseEntity<List<Book>> getBooks(@PathVariable("user_id") Long user_id) {
        return new ResponseEntity<>(orderService.getBooksByUserId(user_id), HttpStatus.OK);
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("order_id") Long order_id) {
        try {
            Order order = orderService.deleteOrderById(order_id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable("user_id") Long user_id) {
        try{
            List<Order> orders = orderService.getOrdersByUserId(user_id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
