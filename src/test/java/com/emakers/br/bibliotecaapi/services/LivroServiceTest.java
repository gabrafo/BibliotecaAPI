package com.emakers.br.bibliotecaapi.services;

import com.emakers.br.bibliotecaapi.domain.dto.request.LivroRequestDTO;
import com.emakers.br.bibliotecaapi.domain.dto.response.LivroResponseDTO;
import com.emakers.br.bibliotecaapi.domain.entities.Livro;
import com.emakers.br.bibliotecaapi.exceptions.general.EntityNotFoundException;
import com.emakers.br.bibliotecaapi.exceptions.livro.InvalidQuantityException;
import com.emakers.br.bibliotecaapi.repositories.LivroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class LivroServiceTest {

    @Mock // Não quero que o BD seja afetado pelas mudanças
    private LivroRepository mockedRepository;

    @InjectMocks
    private LivroService livroService;

    @Test
    @DisplayName("Deve salvar o livro corretamente no BD em memória")
    void LivroService_CreateLivro_ReturnsLivroResponseDTO() {
        LivroRequestDTO livroRequestDTO = new LivroRequestDTO("Livro Teste",
                "Autor Teste", 5, Date.valueOf("2023-01-01"));

        Livro livro = new Livro(livroRequestDTO);
        when(mockedRepository.save(any(Livro.class))).thenReturn(livro);

        LivroResponseDTO result = livroService.createLivro(livroRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.nome()).isEqualTo(livro.getNome());
        assertThat(result.autor()).isEqualTo(livro.getAutor());
        assertThat(result.quantidade()).isEqualTo(livro.getQuantidade());
        assertThat(result.dataLancamento()).isEqualTo(livro.getDataLancamento());
    }

    @Test
    @DisplayName("Deve lançar InvalidQuantityException para quantidade negativa")
    void LivroService_CreateLivro_ThrowsInvalidQuantityException(){
        LivroRequestDTO livroRequestDTO = LivroRequestDTO.testBuilder("Livro Teste",
                "Autor Teste", -1, Date.valueOf("2023-01-01"));

        assertThatThrownBy(() -> livroService.createLivro(livroRequestDTO))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    @DisplayName("Deve retornar todos os livros do BD")
    void LivroService_FindAllLivros_ReturnsLivrosResponseDTO() {
        Livro livro1 = new Livro("Livro 1", "Autor 1", 5, Date.valueOf("2023-01-01"));
        Livro livro2 = new Livro("Livro 2", "Autor 2", 3, Date.valueOf("2023-02-01"));
        List<Livro> livros = List.of(livro1, livro2);
        when(mockedRepository.findAll()).thenReturn(livros); // Repository sempre irá retornar os 2 objetos acima

        List<LivroResponseDTO> result = livroService.findAllLivros();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).nome()).isEqualTo(livro1.getNome());
        assertThat(result.get(0).autor()).isEqualTo(livro1.getAutor());
        assertThat(result.get(0).quantidade()).isEqualTo(livro1.getQuantidade());
        assertThat(result.get(0).dataLancamento()).isEqualTo(livro1.getDataLancamento());

        assertThat(result.get(1).nome()).isEqualTo(livro2.getNome());
        assertThat(result.get(1).autor()).isEqualTo(livro2.getAutor());
        assertThat(result.get(1).quantidade()).isEqualTo(livro2.getQuantidade());
        assertThat(result.get(1).dataLancamento()).isEqualTo(livro2.getDataLancamento());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não há livros no BD")
    void findAllLivros_ReturnsEmptyList() {
        when(mockedRepository.findAll()).thenReturn(new ArrayList<>());

        List<LivroResponseDTO> result = livroService.findAllLivros();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar o livro corretamente")
    void findLivroById_ReturnsLivroResponseDTO() {
        Livro livro = new Livro("Livro", "Autor", 5, Date.valueOf("2023-01-01"));
        livro.setIdLivro(1L);
        when(mockedRepository.findById(livro.getIdLivro())).thenReturn(Optional.of(livro));

        LivroResponseDTO result = livroService.findLivroById(livro.getIdLivro());

        assertThat(result.nome()).isEqualTo(livro.getNome());
        assertThat(result.autor()).isEqualTo(livro.getAutor());
        assertThat(result.quantidade()).isEqualTo(livro.getQuantidade());
        assertThat(result.dataLancamento()).isEqualTo(livro.getDataLancamento());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar buscar o livro por ID")
    void findLivroById_ThrowsEntityNotFoundException(){
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> livroService.findLivroById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Entidade do tipo Livro com o ID 1 não encontrada");
    }
}