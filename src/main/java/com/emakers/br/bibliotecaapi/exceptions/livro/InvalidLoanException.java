package com.emakers.br.bibliotecaapi.exceptions.livro;

public class InvalidLoanException extends RuntimeException {
    public InvalidLoanException(String message) {
        super(message);
    }

}
