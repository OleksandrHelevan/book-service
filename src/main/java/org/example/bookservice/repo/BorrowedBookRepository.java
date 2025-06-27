package org.example.bookservice.repo;

import org.example.bookservice.model.Book;
import org.example.bookservice.model.BorrowedBook;
import org.example.bookservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBookRepository extends JpaRepository<Book,Long> {
    BorrowedBook makeOrder(Book book, User user);
}
