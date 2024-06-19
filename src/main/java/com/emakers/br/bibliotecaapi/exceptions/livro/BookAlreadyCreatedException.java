package com.emakers.br.bibliotecaapi.exceptions.livro;

public class BookAlreadyCreatedException extends RuntimeException {
    public BookAlreadyCreatedException(String message) {
        super(message);
    }
}
