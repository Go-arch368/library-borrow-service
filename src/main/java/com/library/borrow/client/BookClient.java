package com.library.borrow.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "book-service")
public interface BookClient {

    @GetMapping("/api/books/{id}")
    Object getBookById(@PathVariable Long id,
                       @RequestHeader("Authorization") String token); // ✅

    @PutMapping("/api/books/{id}/availability")
    Object updateAvailability(@PathVariable Long id,
                              @RequestParam Boolean available,
                              @RequestHeader("Authorization") String token); // ✅
}