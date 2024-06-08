package com.emakers.br.bibliotecaapi.domain.dto.response;

import com.emakers.br.bibliotecaapi.domain.entities.Pessoa;

public record PessoaResponseDTO(

        Long id,
        String nome,
        String cep
) {
    public PessoaResponseDTO(Pessoa pessoa){
        this(pessoa.getIdPessoa(), pessoa.getNome(), pessoa.getCep());
    }
}
