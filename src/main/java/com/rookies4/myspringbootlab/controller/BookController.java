package com.rookies4.myspringbootlab.controller;

import com.rookies4.myspringbootlab.controller.dto.BookDTO;
import com.rookies4.myspringbootlab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    // 등록
    @PostMapping
    public ResponseEntity<BookDTO.BookResponse> create(
            @Valid @RequestBody BookDTO.BookCreateRequest req) {
        return ResponseEntity.ok(bookService.create(req));
    }

    // 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<BookDTO.BookResponse>> getBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }

    // ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    // ISBN으로 조회
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.BookResponse> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getByIsbn(isbn));
    }

    // 수정(부분 업데이트)
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO.BookUpdateRequest req) {
        return ResponseEntity.ok(bookService.update(id, req));
    }

    // 삭제 (204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}