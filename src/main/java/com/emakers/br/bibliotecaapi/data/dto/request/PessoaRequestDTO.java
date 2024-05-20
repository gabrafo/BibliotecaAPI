package com.emakers.br.bibliotecaapi.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PessoaRequestDTO(
        @NotBlank(message = "Campo 'Nome' não pode estar vazio")
        String nome,

        @NotBlank(message = "Campo 'CEP' não pode estar vazio")
        String cep
) {
}
