package org.example.elasticsearch.repository;

import org.example.elasticsearch.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String> {

    List<Book> findByAuthorName(String author);

    Optional<Book> findByIsbn(String isbn);
}
