package org.example.bookservice.repo;

import org.example.bookservice.model.Book;
import org.example.bookservice.model.Order;
import org.example.bookservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Book,Long> {
    Order makeOrder(User user, Book book);
}
