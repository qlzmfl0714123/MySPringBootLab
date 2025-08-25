package com.rookies4.myspringbootlab.repository;

import com.rookies4.myspringbootlab.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {

    @Query("select d from BookDetail d join fetch d.book where d.id = :id")
    Optional<BookDetail> findByIdWithBook(Long id);

    @Query("select d from BookDetail d where d.book.id = :bookId")
    Optional<BookDetail> findByBookId(Long bookId);

    List<BookDetail> findByPublisher(String publisher);
}