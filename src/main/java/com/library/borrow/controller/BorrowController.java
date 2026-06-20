package com.library.borrow.controller;

import com.library.borrow.model.BorrowRecord;
import com.library.borrow.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/{bookId}/user/{userId}")
    public BorrowRecord borrowBook(@PathVariable Long bookId,
                                   @PathVariable Long userId,
                                   HttpServletRequest request) { // ✅
        // ✅ Get token from request and pass it along
        String token = request.getHeader("Authorization");
        return borrowService.borrowBook(bookId, userId, token);
    }

    @PutMapping("/return/{bookId}")
    public BorrowRecord returnBook(@PathVariable Long bookId,
                                   HttpServletRequest request) { // ✅
        String token = request.getHeader("Authorization");
        return borrowService.returnBook(bookId, token);
    }

    @GetMapping("/user/{userId}")
    public List<BorrowRecord> getMyBooks(@PathVariable Long userId) {
        return borrowService.getMyBorrowedBooks(userId);
    }
}