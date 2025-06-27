package org.example.bookservice.service;

import org.example.bookservice.exception.AmountIsZeroException;
import org.example.bookservice.exception.BookLimitExceededException;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.exception.UserHasBorrowedBooksException;
import org.example.bookservice.model.Book;
import org.example.bookservice.model.Order;
import org.example.bookservice.request.OrderRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OrderService {
    Order makeOrder(OrderRequest orderRequest) throws AmountIsZeroException, ItemNotFoundException, BookLimitExceededException;

    List<Book> getBooksByUserId(Long userId);

    void deleteOrderById(Long id);

    List<Order> getOrdersByUserId(Long userId) throws ItemNotFoundException;

    void deleteUserById(Long userId) throws UserHasBorrowedBooksException;

    List<Book> findBookByUserName(String username);

    Set<String> getAllBorrowedBooksNames();

    Map<Book, Integer> countBorrowedBooksByBooksTitle();
}
