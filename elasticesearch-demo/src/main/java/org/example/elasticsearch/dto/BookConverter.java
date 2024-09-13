package org.example.elasticsearch.dto;

import org.example.elasticsearch.model.Book;

public class BookConverter {

    public static Book transform(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setAuthorName(bookDto.getAuthorName());
        book.setIsbn(bookDto.getIsbn());
        return book;
    }
}
