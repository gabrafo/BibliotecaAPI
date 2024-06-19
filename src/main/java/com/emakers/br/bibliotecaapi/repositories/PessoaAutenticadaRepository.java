package com.emakers.br.bibliotecaapi.repositories;

import com.emakers.br.bibliotecaapi.domain.entities.PessoaAutenticada;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaAutenticadaRepository extends JpaRepository<PessoaAutenticada, Long> {
    PessoaAutenticada findByNomeDeUsuario(String nomeDeUsuario);
}