package com.polarbookshop.catalogservice.controllers;

import com.polarbookshop.catalogservice.model.Book;
import com.polarbookshop.catalogservice.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping(produces = "application/json")
    public List<Book> get(){
        logger.info("Get all books");
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn){
        logger.info("Why not asdjaskdl");
        return bookService.getBookByIsbn(isbn);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(@Valid @RequestBody Book book){
        logger.info("Add a new book: " + book);
        return bookService.addNewBook(book);
    }

    @PutMapping(value = "/{isbn}",consumes = "application/json", produces = "application/json")
    public Book put(@PathVariable String isbn, @Valid @RequestBody Book book){
        logger.info("Update a book: " + book);
        return bookService.updateBook(isbn,book);
    }

    @DeleteMapping(value = "/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn){
        logger.info("Delete a book with ISBN=: " + isbn);
        bookService.deleteBook(isbn);
    }

}
