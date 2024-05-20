package com.emakers.br.bibliotecaapi.exceptions.livro;

public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException() {
        super("Quantidade deve ser maior que zero");
    }
}
