package com.polarbookshop.catalogservice.service;

import com.polarbookshop.catalogservice.exception.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.exception.BookNotFoundException;
import com.polarbookshop.catalogservice.model.Book;
import com.polarbookshop.catalogservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(){
        return (List<Book>) bookRepository.findAll();
    }

    public Book getBookByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).orElseThrow(() ->
                    new BookNotFoundException(isbn)
                );
    }

    public Book addNewBook(Book book){
        if (bookRepository.existsBookByIsbn(book.getIsbn())){
            throw new BookAlreadyExistsException(book.getIsbn());
        }
        return bookRepository.save(book);
    }

    public Book updateBook(String isbn, Book updatedBook){
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(()
                -> new BookNotFoundException(isbn)
        );
        book.setAuthor(updatedBook.getAuthor());
        book.setPrice(updatedBook.getPrice());
        book.setTitle(updatedBook.getTitle());
        return bookRepository.save(book);
    }

    public void deleteBook(String isbn){
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(
                () -> new BookNotFoundException(isbn));
        bookRepository.delete(book);
    }

}
