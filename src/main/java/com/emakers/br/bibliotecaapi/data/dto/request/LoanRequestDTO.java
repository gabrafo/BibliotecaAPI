package com.emakers.br.bibliotecaapi.data.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoanRequestDTO(

        @NotNull(message = "Campo 'idLivro' não pode ser nulo")
        Long idLivro,

        @NotNull(message = "Campo 'idPessoa' não pode ser nulo")
        Long idPessoa
) {
}
