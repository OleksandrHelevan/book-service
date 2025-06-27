package org.example.bookservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.dto.BookDTO;
import org.example.bookservice.model.Book;
import org.example.bookservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book Controller", description = "Endpoints for managing books")
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get all books", description = "Returns a list of all books")
    @ApiResponse(responseCode = "200", description = "List of books returned",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class)))
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @Operation(summary = "Add or update a book", description = "Creates or updates a book record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book successfully created/updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Book> addOrUpdateBook(@RequestBody BookDTO bookDTO) {
        Book savedBook = bookService.addBook(bookDTO);
        return new ResponseEntity<>(savedBook, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing book", description = "Updates a book by title and author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
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
