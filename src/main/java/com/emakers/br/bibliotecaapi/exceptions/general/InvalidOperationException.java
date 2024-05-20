package com.emakers.br.bibliotecaapi.exceptions.general;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
