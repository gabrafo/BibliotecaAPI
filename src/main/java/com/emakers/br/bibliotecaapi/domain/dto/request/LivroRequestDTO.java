package com.emakers.br.bibliotecaapi.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public record LivroRequestDTO(
        @NotBlank(message = "Campo 'Nome' não pode estar vazio")
        String nome,

        @NotBlank(message = "Campo 'Autor' não pode estar vazio")
        String autor,

        @Positive(message = "Campo 'Quantidade' deve ser maior que zero")
        @NotNull(message = "Campo 'Quantidade' não pode estar vazio")
        Integer quantidade,

        @NotNull(message = "Campo 'Data de lançamento' não pode estar vazio")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Date dataLancamento
) {
        // Construtor sem validação usado para testes unitários
        public static LivroRequestDTO testBuilder(String nome, String autor, Integer quantidade, Date dataLancamento) {
                return new LivroRequestDTO(nome, autor, quantidade, dataLancamento);
        }
}
