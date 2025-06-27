package org.example.bookservice.service;

import org.example.bookservice.exception.AmountIsZeroException;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.model.Book;
import org.example.bookservice.model.Order;
import org.example.bookservice.request.OrderRequest;

import java.util.List;

public interface OrderService {
    Order makeOrder(OrderRequest orderRequest) throws AmountIsZeroException, ItemNotFoundException;

    List<Book> getBooksByUserId(Long userId);

    Order deleteOrderById(Long orderId) throws ItemNotFoundException;

    List<Order> getOrdersByUserId(Long userId) throws ItemNotFoundException;
}
