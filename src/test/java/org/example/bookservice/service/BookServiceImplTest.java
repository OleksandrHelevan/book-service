package org.example.bookservice.service;

import org.example.bookservice.dto.BookDTO;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.model.Book;
import org.example.bookservice.repo.BookRepository;
import org.example.bookservice.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    private BookDTO bookDTO;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookDTO = new BookDTO();
        bookDTO.setTitle("Java Basics");
        bookDTO.setAuthor("Jane Smith");
        bookDTO.setAmount(1);

        book = new Book();
        book.setTitle("Java Basics");
        book.setAuthor("Jane Smith");
        book.setAmount(2);
    }

    @Test
    void getBooks_ReturnsBookDTOList() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookDTO> books = bookService.getBooks();

        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(book.getTitle(), books.getFirst().getTitle());

        verify(bookRepository).findAll();
    }

    @Test
    void addBook_IncreasesAmount_WhenBookExists() {
        when(bookRepository.findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book savedBook = bookService.addBook(bookDTO);

        assertNotNull(savedBook);
        assertEquals(book.getAmount(), savedBook.getAmount());

        verify(bookRepository).findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor());
        verify(bookRepository).save(book);
    }

    @Test
    void addBook_SavesNewBook_WhenBookDoesNotExist() {
        bookDTO.setAmount(0);

        when(bookRepository.findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book savedBook = bookService.addBook(bookDTO);

        assertNotNull(savedBook);
        assertEquals(1, savedBook.getAmount());
        assertEquals(bookDTO.getTitle(), savedBook.getTitle());

        verify(bookRepository).findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void updateBook_Success() throws ItemNotFoundException {
        when(bookRepository.findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book updatedBook = bookService.updateBook(bookDTO);

        assertNotNull(updatedBook);
        assertEquals(book.getTitle(), updatedBook.getTitle());

        verify(bookRepository).findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor());
        verify(bookRepository).save(book);
    }

    @Test
    void updateBook_ThrowsItemNotFoundException_WhenBookNotFound() {
        when(bookRepository.findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> bookService.updateBook(bookDTO));

        verify(bookRepository).findByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor());
        verify(bookRepository, never()).save(any());
    }
}
