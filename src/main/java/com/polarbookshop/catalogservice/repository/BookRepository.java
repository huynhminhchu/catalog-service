package com.polarbookshop.catalogservice.repository;

import com.polarbookshop.catalogservice.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);
    Boolean existsBookByIsbn(String isbn);
}
