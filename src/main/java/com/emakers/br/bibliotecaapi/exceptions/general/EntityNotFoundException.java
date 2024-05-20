package com.emakers.br.bibliotecaapi.exceptions.general;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(Long id, String entity) {
        super("Entidade do tipo " + entity + " com o ID " + id + " não encontrada");
    }
}
