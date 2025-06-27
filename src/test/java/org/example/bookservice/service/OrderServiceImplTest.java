package org.example.bookservice.service;

import org.example.bookservice.exception.*;
import org.example.bookservice.model.Book;
import org.example.bookservice.model.Order;
import org.example.bookservice.model.User;
import org.example.bookservice.repo.BookRepository;
import org.example.bookservice.repo.OrderRepository;
import org.example.bookservice.repo.UserRepository;
import org.example.bookservice.request.OrderRequest;
import org.example.bookservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;

    private final Long userId = 1L;
    private final Long bookId = 2L;

    private final User user = new User(userId, "John", null);
    Book book = new Book(bookId, "Java Basics", "Jane Smith", 1);
    private final OrderRequest orderRequest = new OrderRequest(userId, bookId);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(orderService, "borrowLimit", 10);
    }


    @Test
    void getBooksByUserId_Success() {
        List<Book> books = List.of(book);
        when(orderRepository.findBooksByUserId(userId)).thenReturn(Optional.of(books));
        assertEquals(books, orderService.getBooksByUserId(userId));
    }

    @Test
    void getBooksByUserId_NotFound() {
        when(orderRepository.findBooksByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> orderService.getBooksByUserId(userId));
    }

    @Test
    void deleteOrderById_Success() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(orderRepository.existsById(bookId)).thenReturn(true);

        orderService.deleteOrderById(bookId);

        verify(orderRepository).deleteById(bookId);
        assertEquals(2, book.getAmount());
    }

    @Test
    void deleteOrderById_OrderNotFound() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(orderRepository.existsById(bookId)).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> orderService.deleteOrderById(bookId));
    }

    @Test
    void deleteOrderById_BookNotFound() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> orderService.deleteOrderById(bookId));
    }

    @Test
    void getOrdersByUserId_Success() {
        List<Order> orders = List.of(new Order(1L, user, book));
        when(orderRepository.findOrdersByUserId(userId)).thenReturn(Optional.of(orders));

        assertEquals(orders, orderService.getOrdersByUserId(userId));
    }

    @Test
    void getOrdersByUserId_NotFound() {
        when(orderRepository.findOrdersByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> orderService.getOrdersByUserId(userId));
    }

    @Test
    void deleteUserById_Success() {
        when(orderRepository.existsByUserId(userId)).thenReturn(false);
        orderService.deleteUserById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUserById_ThrowsUserHasBorrowedBooksException() {
        when(orderRepository.existsByUserId(userId)).thenReturn(true);
        assertThrows(UserHasBorrowedBooksException.class, () -> orderService.deleteUserById(userId));
    }

    @Test
    void findBookByUserName_ReturnsBooks() {
        List<Book> books = List.of(book);
        when(orderRepository.findBooksByUserName("John")).thenReturn(books);
        assertEquals(books, orderService.findBookByUserName("John"));
    }

    @Test
    void getAllBorrowedBooksNames_ReturnsSet() {
        List<Book> books = List.of(book);
        when(orderRepository.findAllBorrowedBooks()).thenReturn(books);
        assertTrue(orderService.getAllBorrowedBooksNames().contains("Java Basics"));
    }


}
