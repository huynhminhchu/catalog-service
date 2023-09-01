package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.model.Book;
import com.polarbookshop.catalogservice.repository.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@Profile("test")
public class BookDataLoader {

    private final BookRepository bookRepository;


    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData(){
        var book1 = new Book("Harry Potter", "1234567895", "J.K.Rowling", 25.5);
        var book2 = new Book("Gone by the wind", "123456788", "Unknown", 30.0);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

}
