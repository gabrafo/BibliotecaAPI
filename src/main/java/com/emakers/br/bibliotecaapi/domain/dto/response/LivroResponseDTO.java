package com.emakers.br.bibliotecaapi.domain.dto.response;

import com.emakers.br.bibliotecaapi.domain.entities.Livro;
import com.emakers.br.bibliotecaapi.domain.entities.Pessoa;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public record LivroResponseDTO(

        Long id,
        String nome,
        String autor,
        Integer quantidade,
        Date dataLancamento,
        List<PessoaResponseDTO> pessoaResponseDTO
) {
    public LivroResponseDTO(Livro livro) {
        this(livro.getIdLivro(), livro.getNome(), livro.getAutor(), livro.getQuantidade(), livro.getDataLancamento(), mapPessoas(livro.getPessoasEmprestimo()));
    }


    private static List<PessoaResponseDTO> mapPessoas(List<Pessoa> pessoas) {
        if (pessoas == null) {
            return List.of(); // Retorna uma lista vazia se a lista de pessoas for nula
        }
        return pessoas.stream()
                .map(PessoaResponseDTO::new)
                .collect(Collectors.toList());
    }
}
