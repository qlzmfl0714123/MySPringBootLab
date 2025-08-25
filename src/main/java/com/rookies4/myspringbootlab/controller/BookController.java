package com.rookies4.myspringbootlab.controller;

import com.rookies4.myspringbootlab.controller.dto.BookDTO;
import com.rookies4.myspringbootlab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO.Response>> getAll() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.Response> getByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @GetMapping("/search/author")
    public ResponseEntity<List<BookDTO.Response>> searchByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(author));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<BookDTO.Response>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @PostMapping
    public ResponseEntity<BookDTO.Response> create(@RequestBody @Valid BookDTO.Request request) {
        return ResponseEntity.ok(bookService.createBook(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.Response> update(
            @PathVariable Long id,
            @RequestBody @Valid BookDTO.Request request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO.Response> patchBook(
            @PathVariable Long id,
            @RequestBody BookDTO.PatchRequest request) {
        return ResponseEntity.ok(bookService.patchBook(id, request));
    }


    @PatchMapping("/{id}/detail")
    public ResponseEntity<BookDTO.BookDetailResponse> patchDetail(
            @PathVariable Long id,
            @RequestBody BookDTO.BookDetailPatchRequest request) {
        return ResponseEntity.ok(bookService.patchDetail(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}