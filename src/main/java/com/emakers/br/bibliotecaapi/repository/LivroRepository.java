package com.emakers.br.bibliotecaapi.repository;

import com.emakers.br.bibliotecaapi.data.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
