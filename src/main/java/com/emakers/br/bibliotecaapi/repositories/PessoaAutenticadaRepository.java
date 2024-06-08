package com.emakers.br.bibliotecaapi.repositories;

import com.emakers.br.bibliotecaapi.domain.entities.PessoaAutenticada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface PessoaAutenticadaRepository extends JpaRepository<PessoaAutenticada, Long> {
    UserDetails findByNomeDeUsuario(String nomeDeUsuario);
}
