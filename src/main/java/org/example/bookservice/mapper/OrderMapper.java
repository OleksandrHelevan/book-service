package org.example.bookservice.mapper;

import org.example.bookservice.model.Order;
import org.example.bookservice.request.OrderRequest;

public class OrderMapper {

    public static OrderRequest toOrderRequest(Order order) {
        return new OrderRequest(order.getUser().getId(), order.getBook().getId());
    }

}
