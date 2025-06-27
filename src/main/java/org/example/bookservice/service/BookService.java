package org.example.bookservice.service;

import org.example.bookservice.dto.BookDTO;
import org.example.bookservice.model.Book;

import java.util.List;

public interface BookService {
    List<BookDTO> getBooks();
    Book addBook(BookDTO bookDTO);
    Book updateBook(BookDTO bookDTO);
}
