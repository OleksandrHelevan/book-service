package org.example.bookservice.request;


public record OrderRequest(Long bookId, Long userId) {

    public OrderRequest(Long bookId, Long userId) {
        this.bookId = bookId;
        this.userId = userId;
    }
}
