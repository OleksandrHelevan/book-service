package org.example.bookservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="borrowed_book")
@Data
public class BorrowedBook {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}
