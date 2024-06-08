package com.emakers.br.bibliotecaapi.repositories;

import com.emakers.br.bibliotecaapi.domain.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
