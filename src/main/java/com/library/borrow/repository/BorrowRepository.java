package com.library.borrow.repository;

import com.library.borrow.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUserId(Long userId);
    Optional<BorrowRecord> findByBookIdAndReturnDateIsNull(Long bookId);
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}