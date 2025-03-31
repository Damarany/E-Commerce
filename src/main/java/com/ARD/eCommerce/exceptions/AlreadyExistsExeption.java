package com.ARD.eCommerce.exceptions;

public class AlreadyExistsExeption extends RuntimeException {
    public AlreadyExistsExeption(String message) {
        super(message);
    }
}
