package com.library.borrow.service;

import com.library.borrow.client.BookClient;
import com.library.borrow.model.BorrowRecord;
import com.library.borrow.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookClient bookClient; // ✅ calls Book Service!

    public BorrowRecord borrowBook(Long bookId, Long userId) {
        // ✅ Check if book is already borrowed
        if (borrowRepository.existsByBookIdAndReturnDateIsNull(bookId)) {
            throw new RuntimeException("Book is already borrowed!");
        }

        // ✅ Mark book as unavailable in Book Service
        bookClient.updateAvailability(bookId, false);

        // ✅ Create borrow record
        BorrowRecord record = new BorrowRecord();
        record.setBookId(bookId);
        record.setUserId(userId);
        record.setBorrowDate(LocalDate.now());
        record.setReturnDate(null);

        return borrowRepository.save(record);
    }

    public BorrowRecord returnBook(Long bookId) {
        BorrowRecord record = borrowRepository
                .findByBookIdAndReturnDateIsNull(bookId)
                .orElseThrow(() ->
                        new RuntimeException("No active borrow record!"));

        // ✅ Mark book as available in Book Service
        bookClient.updateAvailability(bookId, true);

        record.setReturnDate(LocalDate.now());
        return borrowRepository.save(record);
    }

    public List<BorrowRecord> getMyBorrowedBooks(Long userId) {
        return borrowRepository.findByUserId(userId);
    }
}