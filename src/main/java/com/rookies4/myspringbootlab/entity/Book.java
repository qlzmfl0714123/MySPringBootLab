package com.rookies4.myspringbootlab.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(name = "uk_books_isbn", columnNames = "isbn")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // H2에서도 동작
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "author", nullable = false, length = 100)
    private String author;

    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "price", nullable = false)
    private Integer price;

    protected Book() { /* JPA */ }

    public Book(String title, String author, String isbn, LocalDate publishDate, Integer price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.price = price;
    }

    // --- getters / setters ---
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public LocalDate getPublishDate() { return publishDate; }
    public Integer getPrice() { return price; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
    public void setPrice(Integer price) { this.price = price; }
}