package com.geekbrains.exception;

public class MyArraySizeException extends RuntimeException {

    public MyArraySizeException() {
        super();
    }

    public MyArraySizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyArraySizeException(String message) {
        super(message);
    }
}
