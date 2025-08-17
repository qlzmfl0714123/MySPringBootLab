package com.rookies4.myspringbootlab.repository;

import com.rookies4.myspringbootlab.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // 내장 H2
@ActiveProfiles("test")
@EntityScan(basePackages = "com.rookies4.myspringbootlab.entity")
@EnableJpaRepositories(basePackages = "com.rookies4.myspringbootlab.repository")
@TestPropertySource(properties = {
        // H2 메모리 (MySQL 모드)
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",

        // 하이버네이트 DDL 비활성화 → schema.sql로 스키마 생성
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.format_sql=true",

        // test/resources/schema.sql을 항상 먼저 실행
        "spring.sql.init.mode=always",
        "spring.sql.init.schema-locations=classpath:schema.sql"
})
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book bookA() {
        return new Book(
                "스프링 부트 입문",
                "홍길동",
                "9788956746425",
                LocalDate.of(2025, 5, 7),
                30000
        );
    }

    private Book bookB() {
        return new Book(
                "JPA 프로그래밍",
                "박둘리",
                "9788956746432",
                LocalDate.of(2025, 4, 30),
                35000
        );
    }

    @Test
    @DisplayName("도서 등록 테스트 (testCreateBook)")
    void testCreateBook() {
        var saved = bookRepository.save(bookA());
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(saved.getAuthor()).isEqualTo("홍길동");
        assertThat(saved.getIsbn()).isEqualTo("9788956746425");
        assertThat(saved.getPublishDate()).isEqualTo(LocalDate.of(2025,5,7));
        assertThat(saved.getPrice()).isEqualTo(30000);
    }

    @Test
    @DisplayName("ISBN으로 도서 조회 테스트 (testFindByIsbn)")
    void testFindByIsbn() {
        bookRepository.save(bookA());
        bookRepository.save(bookB());

        var found = bookRepository.findByIsbn("9788956746425");
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(found.get().getAuthor()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("저자명으로 도서 목록 조회 테스트 (testFindByAuthor)")
    void testFindByAuthor() {
        bookRepository.save(new Book("테스트 A", "홍길동", "1111", LocalDate.of(2024,1,1), 10000));
        bookRepository.save(new Book("테스트 B", "홍길동", "2222", LocalDate.of(2024,2,1), 12000));
        bookRepository.save(bookB()); // 박둘리

        List<Book> list = bookRepository.findByAuthor("홍길동");
        assertThat(list).hasSize(2);
        assertThat(list).extracting(Book::getTitle)
                .containsExactlyInAnyOrder("테스트 A", "테스트 B");
    }

    @Test
    @DisplayName("도서 정보 수정 테스트 (testUpdateBook)")
    void testUpdateBook() {
        var saved = bookRepository.save(bookA());
        saved.setTitle("스프링 부트 완전입문");
        saved.setPrice(32000);
        var updated = bookRepository.save(saved);

        assertThat(updated.getTitle()).isEqualTo("스프링 부트 완전입문");
        assertThat(updated.getPrice()).isEqualTo(32000);
        assertThat(updated.getIsbn()).isEqualTo("9788956746425");
    }

    @Test
    @DisplayName("도서 삭제 테스트 (testDeleteBook)")
    void testDeleteBook() {
        var saved = bookRepository.save(bookA());
        Long id = saved.getId();

        bookRepository.deleteById(id);

        assertThat(bookRepository.findById(id)).isEmpty();
    }
}