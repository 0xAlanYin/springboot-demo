package org.example.elasticsearch.servcie;

import org.example.elasticsearch.exception.BookNotFoundException;
import org.example.elasticsearch.exception.DuplicateIsbnException;
import org.example.elasticsearch.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getByIsbn(String isbn);

    List<Book> getAll();

    List<Book> findByAuthor(String authorName);

    List<Book> findByTitleAndAuthor(String title, String authorName);

    Book create(Book book) throws DuplicateIsbnException;

    void deleteById(String id);

    Book update(String id, Book book) throws BookNotFoundException;
}
