package com.emakers.br.bibliotecaapi.repository;

import com.emakers.br.bibliotecaapi.data.entities.PessoaAutenticada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface PessoaAutenticadaRepository extends JpaRepository<PessoaAutenticada, Long> {
    UserDetails findByNomeDeUsuario(String nomeDeUsuario);
}
