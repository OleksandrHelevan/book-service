package org.example.bookservice.repo;

import org.example.bookservice.model.Book;
import org.example.bookservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT o.book FROM Order o WHERE o.user.id = :userId")
    Optional<List<Book>> findBooksByUserId(@Param("userId") Long userId);

    Optional<List<Order>> findOrdersByUserId(Long userId);

    boolean existsByUserId(Long userId);

    int countByUserId(Long userId);

    @Query("SELECT o.book FROM Order o WHERE o.user.name = :userName")
    List<Book> findBooksByUserName(String userName);

    @Query("SELECT o.book FROM Order o")
    List<Book> findAllBorrowedBooks();

    @Query("SELECT o.book, COUNT(o) FROM Order o GROUP BY o.book")
    List<Object[]> countBorrowedBooksByBooksTitle();

}
