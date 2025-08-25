package com.rookies4.myspringbootlab.controller.dto;

import com.rookies4.myspringbootlab.entity.Book;
import com.rookies4.myspringbootlab.entity.BookDetail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;

public class BookDTO {

    // === POST/PUT 요청용 ===
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "Book title is required")
        private String title;

        @NotBlank(message = "Author name is required")
        private String author;

        @NotBlank(message = "ISBN is required")
        @Pattern(
                regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$",
                message = "ISBN must be valid (10 or 13 digits, with or without hyphens)"
        )
        private String isbn;

        @PositiveOrZero(message = "Price must be positive or zero")
        private Integer price;

        @Past(message = "Publish date must be in the past")
        private LocalDate publishDate;

        @Valid
        private BookDetailDTO detailRequest;
    }

    // BookDetail 요청 DTO
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailDTO {
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }

    // === PATCH 요청용 (부분수정) ===
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PatchRequest {
        private String title;
        private String author;

        @Pattern(
                regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$",
                message = "ISBN must be valid (10 or 13 digits, with or without hyphens)"
        )
        private String isbn;

        @PositiveOrZero(message = "Price must be positive or zero")
        private Integer price;

        private LocalDate publishDate;

        @Valid
        private BookDetailPatchRequest detailRequest;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailPatchRequest {
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }

    // === 응답 DTO ===
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
        private BookDetailResponse detail;

        public static Response fromEntity(Book book) {
            BookDetailResponse detailResponse = (book.getBookDetail() != null)
                    ? BookDetailResponse.fromEntity(book.getBookDetail())
                    : null;

            return Response.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .publishDate(book.getPublishDate())
                    .detail(detailResponse)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailResponse {
        private Long id;
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;

        // ✅ Service에서 사용하기 위한 정적 팩토리
        public static BookDetailResponse fromEntity(BookDetail d) {
            if (d == null) return null;
            return BookDetailResponse.builder()
                    .id(d.getId())
                    .description(d.getDescription())
                    .language(d.getLanguage())
                    .pageCount(d.getPageCount())
                    .publisher(d.getPublisher())
                    .coverImageUrl(d.getCoverImageUrl())
                    .edition(d.getEdition())
                    .build();
        }
    }
}
