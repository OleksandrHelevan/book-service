package org.example.bookservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.model.User;
import org.example.bookservice.repo.BorrowedBookRepository;
import org.example.bookservice.service.BorrowedBookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowedBookServiceImpl implements BorrowedBookService {

    private final BorrowedBookRepository borrowedBookRepository;

}
