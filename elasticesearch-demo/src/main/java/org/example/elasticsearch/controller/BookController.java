package org.example.elasticsearch.controller;

import java.util.List;
import javax.validation.Valid;
import org.example.elasticsearch.dto.BookConverter;
import org.example.elasticsearch.dto.BookDto;
import org.example.elasticsearch.exception.BookNotFoundException;
import org.example.elasticsearch.exception.DuplicateIsbnException;
import org.example.elasticsearch.model.Book;
import org.example.elasticsearch.servcie.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/books")
public class BookController {

  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  //  curl -X GET "http://localhost:8080/v1/books" -H "accept: application/json"
  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<Book> getAllBooks() {
    return bookService.getAll();
  }

  //    curl -X POST "http://localhost:8080/v1/books" \
  //            -H "accept: application/json" \
  //            -H "Content-Type: application/json" \
  //            -d '{
  //            "title": "Sample Book",
  //            "author": "John Doe",
  //            "isbn": "1234567890",
  //            "publicationYear": 2023
  // }'
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping
  public Book createBook(@Valid @RequestBody BookDto book) throws DuplicateIsbnException {
    return bookService.create(BookConverter.transform(book));
  }

  // curl -X GET "http://localhost:8080/v1/books/1234567890" -H "accept: application/json"
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/{isbn}")
  public Book getBookByIsbn(@PathVariable String isbn) throws BookNotFoundException {
    return bookService
        .getByIsbn(isbn)
        .orElseThrow(() -> new BookNotFoundException("The given isbn is invalid"));
  }

  // curl -X GET "http://localhost:8080/v1/books/query?title=Sample%20Book&author=John%20Doe" -H
  // "accept: application/json"
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/query")
  public List<Book> getBooksByAuthorAndTitle(
      @RequestParam(value = "title") String title, @RequestParam(value = "author") String author) {
    return bookService.findByTitleAndAuthor(title, author);
  }

  //    curl -X PUT "http://localhost:8080/v1/books/{id}" \
  //            -H "accept: application/json" \
  //            -H "Content-Type: application/json" \
  //            -d '{
  //            "title": "Updated Book",
  //            "author": "Jane Doe",
  //            "isbn": "1234567890",
  //            "publicationYear": 2024
  // }'
  @ResponseStatus(HttpStatus.OK)
  @PutMapping(value = "/{id}")
  public Book updateBook(@PathVariable String id, @RequestBody BookDto book)
      throws BookNotFoundException {
    return bookService.update(id, BookConverter.transform(book));
  }

  //  curl -X DELETE "http://localhost:8080/v1/books/1" -H "accept: application/json"
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(value = "/{id}")
  public void deleteBook(@PathVariable String id) {
    bookService.deleteById(id);
  }
}
