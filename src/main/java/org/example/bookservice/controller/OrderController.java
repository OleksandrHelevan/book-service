package org.example.bookservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bookservice.exception.AmountIsZeroException;
import org.example.bookservice.exception.BookLimitExceededException;
import org.example.bookservice.exception.ItemNotFoundException;
import org.example.bookservice.exception.UserHasBorrowedBooksException;
import org.example.bookservice.model.Book;
import org.example.bookservice.model.Order;
import org.example.bookservice.request.OrderRequest;
import org.example.bookservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Tag(name = "Order Controller", description = "Operations related to book orders and users")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create a new order", description = "Create a book order for a user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "User or Book not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Amount is zero", content = @Content),
            @ApiResponse(responseCode = "409", description = "Book limit exceeded", content = @Content)
    })
    @PostMapping("orders")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            Order order = orderService.makeOrder(orderRequest);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AmountIsZeroException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (BookLimitExceededException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Get books borrowed by user ID", description = "Returns a list of books borrowed by the specified user")
    @ApiResponse(responseCode = "200", description = "List of borrowed books",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @GetMapping("user/{user_id}")
    public ResponseEntity<List<Book>> getBooks(
            @Parameter(description = "ID of the user") @PathVariable("user_id") Long user_id) {
        return new ResponseEntity<>(orderService.getBooksByUserId(user_id), HttpStatus.OK);
    }

    @Operation(summary = "Delete order by order ID", description = "Deletes a specific order by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order deleted successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @DeleteMapping("order/{order_id}")
    @Transactional
    public ResponseEntity<String> deleteOrder(
            @Parameter(description = "ID of the order to delete") @PathVariable("order_id") Long order_id) {
        try {
            orderService.deleteOrderById(order_id);
            return new ResponseEntity<>("order with id " + order_id + " was deleted", HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get orders by user ID", description = "Retrieve all orders made by a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of orders",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("order/{user_id}")
    public ResponseEntity<List<Order>> getOrders(
            @Parameter(description = "ID of the user") @PathVariable("user_id") Long user_id) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(user_id);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete user by user ID", description = "Deletes a user if they have no borrowed books")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "User has borrowed books and cannot be deleted", content = @Content)
    })
    @DeleteMapping("user/{user_id}")
    @Transactional
    public ResponseEntity<String> deleteUser(
            @Parameter(description = "ID of the user to delete") @PathVariable("user_id") Long user_id) {
        try {
            orderService.deleteUserById(user_id);
            return new ResponseEntity<>("order with id " + user_id + " was deleted", HttpStatus.OK);
        } catch (UserHasBorrowedBooksException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get books by username", description = "Retrieve a list of books borrowed by the specified username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of books",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "No books found for username", content = @Content)
    })
    @GetMapping("books/{username}")
    public ResponseEntity<List<Book>> getBooksByUsername(
            @Parameter(description = "Username to query books for") @PathVariable("username") String username) {
        List<Book> books = orderService.findBookByUserName(username);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Get all borrowed book names", description = "Retrieve names of all borrowed books")
    @ApiResponse(responseCode = "200", description = "Set of borrowed book names",
            content = @Content(mediaType = "application/json"))
    @GetMapping("books/borrowed")
    public ResponseEntity<Set<String>> getAllBorrowedBooksNames() {
        return new ResponseEntity<>(orderService.getAllBorrowedBooksNames(), HttpStatus.OK);
    }

    @Operation(summary = "Get borrowed book counts by title", description = "Returns a map of books and their borrowed counts")
    @ApiResponse(responseCode = "200", description = "Map of books and borrowed counts",
            content = @Content(mediaType = "application/json"))
    @GetMapping("books/borrowed-amount")
    public ResponseEntity<Map<Book, Integer>> getAllBorrowedBooksAmount() {
        return new ResponseEntity<>(orderService.countBorrowedBooksByBooksTitle(), HttpStatus.OK);
    }
}
