package com.rookies4.myspringbootlab.controller.dto;

import com.rookies4.myspringbootlab.entity.Book;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

public class BookDTO {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class BookCreateRequest {
        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        private String title;

        @NotBlank(message = "저자는 필수 입력 항목입니다.")
        private String author;

        @NotBlank(message = "ISBN은 필수 입력 항목입니다.")
        private String isbn;

        @Positive(message = "가격은 양수여야 합니다.")
        private Integer price;

        @NotNull(message = "출판일자는 필수 입력 항목입니다.")
        private LocalDate publishDate;

        public Book toEntity() {
            Book book = new Book();
            book.setTitle(this.title);
            book.setAuthor(this.author);
            book.setIsbn(this.isbn);
            book.setPrice(this.price);
            book.setPublishDate(this.publishDate);
            return book;
        }
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class BookUpdateRequest {

        @Size(min = 1, message = "제목이 비어있으면 안 됩니다.")
        private String title;

        @Size(min = 1, message = "저자가 비어있으면 안 됩니다.")
        private String author;

        @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
        private Integer price;

        private LocalDate publishDate;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public static BookResponse from(Book book) {
            return new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getPrice(),
                    book.getPublishDate()
            );
        }
    }
}