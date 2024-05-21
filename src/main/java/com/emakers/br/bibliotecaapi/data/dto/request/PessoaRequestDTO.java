package com.emakers.br.bibliotecaapi.data.dto.request;

import com.emakers.br.bibliotecaapi.data.enums.PessoaAutenticadaRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PessoaRequestDTO(
        @NotBlank(message = "Campo 'Nome' não pode estar vazio")
        String nome,

        @NotBlank(message = "Campo 'CEP' não pode estar vazio")
        String cep,

        @NotNull(message = "Campos relacionados à autentificação devem ser preenchidos corretamente")
        PessoaAutenticadaRequestDTO pessoaAutenticadaRequestDTO,

        @NotNull(message = "Campo 'Role' deve ser preenchido corretamente")
        PessoaAutenticadaRole role
) {
}
