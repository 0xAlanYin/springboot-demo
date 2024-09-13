package org.example.elasticsearch.controller.exception.handler;

import lombok.Getter;
import lombok.Setter;
import org.example.elasticsearch.exception.BookNotFoundException;
import org.example.elasticsearch.exception.DuplicateIsbnException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookControllerExceptionHandler {

    @ExceptionHandler(value = {BookNotFoundException.class, DuplicateIsbnException.class})
    public ResponseEntity<Body> doHandlerBookException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Body(exception.getMessage()));
    }

    @Getter
    @Setter
    public static class Body {
        private String message;

        public Body(String message) {
            this.message = message;
        }
    }
}
