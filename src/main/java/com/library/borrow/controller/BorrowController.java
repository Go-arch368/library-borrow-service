package com.library.borrow.controller;

import com.library.borrow.model.BorrowRecord;
import com.library.borrow.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    // POST borrow a book
    @PostMapping("/{bookId}/user/{userId}")
    public BorrowRecord borrowBook(@PathVariable Long bookId,
                                   @PathVariable Long userId) {
        return borrowService.borrowBook(bookId, userId);
    }

    // PUT return a book
    @PutMapping("/return/{bookId}")
    public BorrowRecord returnBook(@PathVariable Long bookId) {
        return borrowService.returnBook(bookId);
    }

    // GET my borrowed books
    @GetMapping("/user/{userId}")
    public List<BorrowRecord> getMyBooks(@PathVariable Long userId) {
        return borrowService.getMyBorrowedBooks(userId);
    }
}