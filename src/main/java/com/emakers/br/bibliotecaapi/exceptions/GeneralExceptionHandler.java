package com.emakers.br.bibliotecaapi.exceptions;

import com.emakers.br.bibliotecaapi.exceptions.general.InvalidOperationException;
import com.emakers.br.bibliotecaapi.exceptions.livro.InvalidLoanException;
import com.emakers.br.bibliotecaapi.exceptions.livro.InvalidQuantityException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<RestErrorMessage> entityNotFoundException(EntityNotFoundException e) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(InvalidOperationException.class)
    private ResponseEntity<RestErrorMessage> invalidOperation(InvalidOperationException e) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(InvalidLoanException.class)
    private ResponseEntity<RestErrorMessage> invalidLoan(Exception e) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(InvalidQuantityException.class)
    private ResponseEntity<RestErrorMessage> invalidQuantity(Exception e) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }
}
