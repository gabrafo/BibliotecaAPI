package com.emakers.br.bibliotecaapi.repository;

import com.emakers.br.bibliotecaapi.data.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
