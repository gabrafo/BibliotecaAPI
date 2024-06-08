package com.emakers.br.bibliotecaapi.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record EmprestimoRequestDTO(

        @NotNull(message = "Campo 'idLivro' não pode ser nulo")
        Long idLivro,

        @NotNull(message = "Campo 'idPessoa' não pode ser nulo")
        Long idPessoa
) {
}
