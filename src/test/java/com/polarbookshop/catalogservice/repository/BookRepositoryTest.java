package com.polarbookshop.catalogservice.repository;

import com.polarbookshop.catalogservice.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("integration")
@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findByIsbn() {
        Book newBook = new Book("Test1", "1234567890 ", "Author",9.90);

        Book savedBook = bookRepository.save(newBook);

        Optional<Book> actualBook = bookRepository.findByIsbn(savedBook.getIsbn());
        assertEquals(savedBook.getIsbn(),actualBook.get().getIsbn());
    }

    @Test
    void existsBookByIsbn() {
        Book newBook = new Book("Test1", "1234567890 ", "Author",9.90);
        Book savedBook = bookRepository.save(newBook);

        assertTrue(bookRepository.existsBookByIsbn(newBook.getIsbn()));
    }
}