package com.library.borrow.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "book-service")  // ✅ calls BOOK-SERVICE via Eureka!
public interface BookClient {

    // Check if book is available
    @GetMapping("/api/books/{id}")
    Object getBookById(@PathVariable Long id);

    // Update book availability
    @PutMapping("/api/books/{id}/availability")
    Object updateAvailability(@PathVariable Long id,
                              @RequestParam Boolean available);
}