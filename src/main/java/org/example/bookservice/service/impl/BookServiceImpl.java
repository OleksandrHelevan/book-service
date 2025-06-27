package org.example.bookservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.dto.BookDTO;
import org.example.bookservice.mapper.BookMapper;
import org.example.bookservice.model.Book;
import org.example.bookservice.repo.BookRepository;
import org.example.bookservice.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<BookDTO> getBooks() {
        return bookRepository.findAll().stream().map(BookMapper::toDto).toList();
    }

    @Override
    public Book addBook(BookDTO bookDTO) {
        Book book = bookRepository.findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor()).orElse(null);
        if (book != null) {
            book.setAmount(book.getAmount() + 1);
            return bookRepository.save(book);
        } else {
            if(bookDTO.getAmount() == 0)
                bookDTO.setAmount(1);
            return bookRepository.save(BookMapper.toEntity(bookDTO));
        }
    }


}
