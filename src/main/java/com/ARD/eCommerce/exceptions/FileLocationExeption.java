package com.ARD.eCommerce.exceptions;

import java.io.IOException;

public class FileLocationExeption extends RuntimeException {
    public FileLocationExeption(String message) {
        super(message);
    }
}
