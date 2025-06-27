package org.example.bookservice.repo;

import org.example.bookservice.model.Book;
import org.example.bookservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT o.book FROM Order o WHERE o.user.id = :userId")
    Optional<List<Book>> findBooksByUserId(@Param("userId") Long userId);

    Optional<List<Order>> findOrdersByUserId(Long userId);
}
