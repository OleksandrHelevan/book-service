package org.example.bookservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.dto.BookDTO;
import org.example.bookservice.model.Book;
import org.example.bookservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addOrUpdateBook(@RequestBody BookDTO bookDTO) {
        Book savedBook = bookService.addBook(bookDTO);
        return new ResponseEntity<>(savedBook, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody BookDTO bookDTO) {
        try {
            Book book = bookService.updateBook(bookDTO);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
