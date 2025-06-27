package org.example.bookservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.exception.BookLimitExceededException;
import org.example.bookservice.exception.UserHasBorrowedBooksException;
import org.springframework.beans.factory.annotation.Value;
import org.example.bookservice.exception.AmountIsZeroException;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.model.Book;
import org.example.bookservice.model.Order;
import org.example.bookservice.model.User;
import org.example.bookservice.repo.BookRepository;
import org.example.bookservice.repo.OrderRepository;
import org.example.bookservice.repo.UserRepository;
import org.example.bookservice.request.OrderRequest;
import org.example.bookservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    @Value("${library.borrow.limit}")
    private int borrowLimit;

    @Override
    public Order makeOrder(OrderRequest orderRequest) throws AmountIsZeroException, ItemNotFoundException, BookLimitExceededException {
        if (orderRepository.countByUserId(orderRequest.userId()) >= borrowLimit)
            throw new BookLimitExceededException("Book limit exceeded");

        User user = userRepository.findById(orderRequest.userId())
                .orElseThrow(() -> new ItemNotFoundException("User not found"));

        Book book = bookRepository.findById(orderRequest.bookId())
                .orElseThrow(() -> new ItemNotFoundException("Book not found"));

        if (book.getAmount() > 0) {
            book.setAmount(book.getAmount() - 1);
        } else {
            throw new AmountIsZeroException("Amount is zero");
        }

        return orderRepository.save(new Order(null, user, book));
    }

    @Override
    public List<Book> getBooksByUserId(Long userId) {
        return orderRepository
                .findBooksByUserId(userId)
                .orElseThrow(() -> new ItemNotFoundException("Books not found"));
    }

    @Override
    public void deleteOrderById(Long orderId) throws ItemNotFoundException {
        Book book = bookRepository.findById(orderId)
                .orElseThrow(() -> new ItemNotFoundException("Book not found"));
        book.setAmount(book.getAmount() + 1);
        if (orderRepository.existsById(orderId))
            orderRepository.deleteById(orderId);
        else throw new ItemNotFoundException("Order not found");
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) throws ItemNotFoundException {
        return orderRepository.findOrdersByUserId(userId)
                .orElseThrow(() -> new ItemNotFoundException("Orders not found"));
    }

    public void deleteUserById(Long userId) throws UserHasBorrowedBooksException {
        if (orderRepository.existsByUserId(userId))
            throw new UserHasBorrowedBooksException("User has borrowed books");
        userRepository.deleteById(userId);
    }

    @Override
    public List<Book> findBookByUserName(String username) {
        return orderRepository.findBooksByUserName(username);
    }

    @Override
    public Set<String> getAllBorrowedBooksNames() {
        return orderRepository.findAllBorrowedBooks()
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toSet());
    }

    @Override
    public Map<Book, Integer> countBorrowedBooksByBooksTitle() {
        List<Object[]> result = orderRepository.countBorrowedBooksByBooksTitle();

        return result.stream()
                .collect(Collectors.toMap(
                        row -> (Book) row[0],
                        row -> ((Long) row[1]).intValue()
                ));
    }


}

