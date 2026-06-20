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
    private BookClient bookClient;

    public BorrowRecord borrowBook(Long bookId, Long userId,
                                   String token) { // ✅ accept token
        if (borrowRepository.existsByBookIdAndReturnDateIsNull(bookId)) {
            throw new RuntimeException("Book is already borrowed!");
        }

        // ✅ Pass token to Book Service
        bookClient.updateAvailability(bookId, false, token);

        BorrowRecord record = new BorrowRecord();
        record.setBookId(bookId);
        record.setUserId(userId);
        record.setBorrowDate(LocalDate.now());
        record.setReturnDate(null);

        return borrowRepository.save(record);
    }

    public BorrowRecord returnBook(Long bookId, String token) { // ✅ accept token
        BorrowRecord record = borrowRepository
                .findByBookIdAndReturnDateIsNull(bookId)
                .orElseThrow(() ->
                        new RuntimeException("No active borrow record!"));

        // ✅ Pass token to Book Service
        bookClient.updateAvailability(bookId, true, token);

        record.setReturnDate(LocalDate.now());
        return borrowRepository.save(record);
    }

    public List<BorrowRecord> getMyBorrowedBooks(Long userId) {
        return borrowRepository.findByUserId(userId);
    }
}