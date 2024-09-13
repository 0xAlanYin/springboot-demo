package org.example.elasticsearch.exception;

public class DuplicateIsbnException extends Exception{

    public DuplicateIsbnException(String message) {
        super(message);
    }
}
