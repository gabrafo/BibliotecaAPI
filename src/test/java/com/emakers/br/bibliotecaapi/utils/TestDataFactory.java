package com.emakers.br.bibliotecaapi.utils;

import com.emakers.br.bibliotecaapi.domain.entities.Livro;
import com.emakers.br.bibliotecaapi.domain.entities.Pessoa;
import com.emakers.br.bibliotecaapi.domain.entities.PessoaAutenticada;
import com.emakers.br.bibliotecaapi.domain.enums.PessoaAutenticadaRole;

import java.sql.Date;

public class TestDataFactory {
    public static Pessoa createSamplePessoa() {
        Pessoa p = new Pessoa();
        p.setIdPessoa(1L);
        p.setNome("Fulano");
        p.setCep("CEP");

        PessoaAutenticada autenticada = new PessoaAutenticada("gabrafo", "garfield");
        autenticada.setPessoa(p); // Estabelece a relação bidirecional
        autenticada.setRole(PessoaAutenticadaRole.ADMIN);

        p.setPessoaAutenticada(autenticada);

        return p;
    }

    public static Livro createSampleLivro() {
        Livro l = new Livro();
        l.setIdLivro(1L);
        l.setNome("Livro");
        l.setAutor("Autor");
        l.setQuantidade(1);
        l.setDataLancamento(Date.valueOf("2023-01-01"));
        return l;
    }
}