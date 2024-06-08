package com.emakers.br.bibliotecaapi.repositories;

import com.emakers.br.bibliotecaapi.domain.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
