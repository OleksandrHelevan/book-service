package org.example.bookservice.service;

import org.example.bookservice.model.Order;
import org.example.bookservice.request.OrderRequest;

public interface OrderService {
    Order makeOrder(OrderRequest orderRequest);
}
