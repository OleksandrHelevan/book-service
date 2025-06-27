package org.example.bookservice.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bookservice.exception.AmountIsZeroException;
import org.example.bookservice.exception.BookLimitExceededException;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.exception.UserHasBorrowedBooksException;
import org.example.bookservice.model.Book;
import org.example.bookservice.model.Order;
import org.example.bookservice.request.OrderRequest;
import org.example.bookservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController()
@RequestMapping("/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("orders")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            Order order = orderService.makeOrder(orderRequest);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AmountIsZeroException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (BookLimitExceededException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("user/{user_id}")
    public ResponseEntity<List<Book>> getBooks(@PathVariable("user_id") Long user_id) {
        return new ResponseEntity<>(orderService.getBooksByUserId(user_id), HttpStatus.OK);
    }

    @DeleteMapping("order/{order_id}")
    @Transactional
    public ResponseEntity<String> deleteOrder(@PathVariable("order_id") Long order_id) {
        try {
            orderService.deleteOrderById(order_id);
            return new ResponseEntity<>("order with id " + order_id + " was deleted", HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("order/{user_id}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable("user_id") Long user_id) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(user_id);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("user/{user_id}")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") Long user_id) {
        try {
            orderService.deleteUserById(user_id);
            return new ResponseEntity<>("order with id " + user_id + " was deleted", HttpStatus.OK);
        } catch (UserHasBorrowedBooksException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("books/{username}")
    public ResponseEntity<List<Book>> getBooksByUsername(@PathVariable("username") String username) {
        List<Book> books = orderService.findBookByUserName(username);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("books/borrowed")
    public ResponseEntity<Set<String>> getAllBorrowedBooksNames() {
        return new ResponseEntity<>(orderService.getAllBorrowedBooksNames(), HttpStatus.OK);
    }


    @GetMapping("books/borrowed-amount")
    public ResponseEntity<Map<Book, Integer>> getAllBorrowedBooksAmount() {
        return new ResponseEntity<>(orderService.countBorrowedBooksByBooksTitle(), HttpStatus.OK);
    }

}
