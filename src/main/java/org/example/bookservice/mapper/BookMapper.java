package org.example.bookservice.mapper;

import org.example.bookservice.dto.BookDTO;
import org.example.bookservice.model.Book;

public class BookMapper {

    public static BookDTO toDto(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setAmount(book.getAmount());
        return bookDTO;
    }

    public static Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setAmount(bookDTO.getAmount());
        return book;
    }
}
