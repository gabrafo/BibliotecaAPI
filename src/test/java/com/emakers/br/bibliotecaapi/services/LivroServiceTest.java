package com.emakers.br.bibliotecaapi.services;

import com.emakers.br.bibliotecaapi.domain.dto.request.LivroRequestDTO;
import com.emakers.br.bibliotecaapi.domain.dto.response.LivroResponseDTO;
import com.emakers.br.bibliotecaapi.domain.dto.response.PessoaResponseDTO;
import com.emakers.br.bibliotecaapi.domain.entities.Livro;
import com.emakers.br.bibliotecaapi.domain.entities.Pessoa;
import com.emakers.br.bibliotecaapi.exceptions.general.EntityNotFoundException;
import com.emakers.br.bibliotecaapi.exceptions.general.InvalidOperationException;
import com.emakers.br.bibliotecaapi.exceptions.livro.InvalidLoanException;
import com.emakers.br.bibliotecaapi.exceptions.livro.InvalidQuantityException;
import com.emakers.br.bibliotecaapi.repositories.LivroRepository;
import com.emakers.br.bibliotecaapi.repositories.PessoaRepository;
import com.emakers.br.bibliotecaapi.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class LivroServiceTest {

    @Mock // Não quero que o BD seja afetado pelas mudanças
    private LivroRepository mockedRepository;

    @Mock
    private PessoaRepository mockedPessoaRepository;

    @InjectMocks
    private LivroService serviceUnderTest;

    @Test
    @DisplayName("Deve criar um livro igual ao objeto de comparação")
    void deveCriarLivro() {
        // given
        LivroRequestDTO livroRequestDTO = new LivroRequestDTO("Livro Teste",
                "Autor Teste", 5, Date.valueOf("2023-01-01"));

        Livro livroComparacao = new Livro(livroRequestDTO);

        // when
        serviceUnderTest.createLivro(livroRequestDTO);

        // then
        ArgumentCaptor<Livro> livroArgumentCaptor = ArgumentCaptor.forClass(Livro.class);

        verify(mockedRepository).save(livroArgumentCaptor.capture());

        Livro capturedLivro = livroArgumentCaptor.getValue();

        assertThat(capturedLivro).isEqualTo(livroComparacao);
    }

    @Test
    @DisplayName("Deve lançar InvalidQuantityException para quantidade negativa")
    void naoDeveCriarLivroComQuantidadeNegativa(){
        LivroRequestDTO livroRequestDTO = LivroRequestDTO.testBuilder("Livro Teste",
                "Autor Teste", -1, Date.valueOf("2023-01-01"));

        assertThatThrownBy(() -> serviceUnderTest.createLivro(livroRequestDTO))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    @DisplayName("Deve chamar corretamente o método do repositório")
    void deveEncontrarTodosOsLivros() {
        // when
        serviceUnderTest.findAllLivros();

        // then
        verify(mockedRepository).findAll(); // Checaremos se o repositório foi chamado e o findAll foi invocado
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não há livros no BD")
    void deveEncontrarListaVaziaDeLivros() {
        // when
        when(mockedRepository.findAll()).thenReturn(List.of());

        // then
        List<LivroResponseDTO> result = serviceUnderTest.findAllLivros();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Deve encontrar corretamente o livro passado no teste e retornar seus dados")
    void deveEncontrarLivroPorId() {
        // given
        Livro livro = TestDataFactory.createSampleLivro();
        Pessoa pessoa = TestDataFactory.createSamplePessoa();
        livro.setPessoasEmprestimo(Collections.singletonList(pessoa));

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(livro));

        LivroResponseDTO result = serviceUnderTest.findLivroById(1L);
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(livro.getIdLivro());
        assertThat(result.nome()).isEqualTo(livro.getNome());
        assertThat(result.autor()).isEqualTo(livro.getAutor());
        assertThat(result.quantidade()).isEqualTo(livro.getQuantidade());
        assertThat(result.dataLancamento()).isEqualTo(livro.getDataLancamento());

        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO(pessoa);
        assertThat(result.pessoaResponseDTO()).containsExactly(pessoaResponseDTO);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar buscar o livro por ID")
    void naoDeveEncontrarLivroInexistentePorId(){
        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> serviceUnderTest.findLivroById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Entidade do tipo Livro com o ID 1 não encontrada");
    }

    @Test
    @DisplayName("Deve atualizar corretamente o livro e retornar seus dados")
    void deveAtualizarLivro() {
        // given
        Livro livro = TestDataFactory.createSampleLivro();
        Pessoa pessoa = TestDataFactory.createSamplePessoa();
        livro.setPessoasEmprestimo(Collections.singletonList(pessoa));

        LivroRequestDTO request = new LivroRequestDTO("Livrinho", "Autor", 5, Date.valueOf("2023-01-01"));

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(livro));
        when(mockedRepository.save(any(Livro.class))).thenReturn(livro);
        LivroResponseDTO updatedData = serviceUnderTest.updateLivro(livro.getIdLivro(), request);

        // then
        assertThat(updatedData).isNotNull();
        assertThat(updatedData.id()).isEqualTo(livro.getIdLivro());
        assertThat(updatedData.nome()).isEqualTo(livro.getNome());
        assertThat(updatedData.autor()).isEqualTo(livro.getAutor());
        assertThat(updatedData.quantidade()).isEqualTo(livro.getQuantidade());
        assertThat(updatedData.dataLancamento()).isEqualTo(livro.getDataLancamento());

        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO(pessoa);
        assertThat(updatedData.pessoaResponseDTO()).containsExactly(pessoaResponseDTO);
    }

    @Test
    @DisplayName("Deve lançar uma exceção EntityNotFoundException ao tentar atualizar um livro não existente")
    void naoDeveAtualizarLivroInexistente() {
        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> serviceUnderTest.updateLivro(1L, new LivroRequestDTO("Livro", "Autor", 1, Date.valueOf("2023-01-01"))))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Entidade do tipo Livro com o ID 1 não encontrada");

        verify(mockedRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar uma exceção InvalidQuantityException ao tentar atualizar um livro usando uma quantidade negativa")
    void naoDeveAtualizarLivroComQuantidadeNegativa() {
        // given
        Livro livro = TestDataFactory.createSampleLivro();

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(livro));

        // then
        LivroRequestDTO request = LivroRequestDTO.testBuilder("Livro", "Autor", -1, Date.valueOf("2023-01-01"));
        assertThatThrownBy(() -> serviceUnderTest.updateLivro(livro.getIdLivro(), request))
                .isInstanceOf(InvalidQuantityException.class)
                .hasMessageContaining("Quantidade deve ser maior que zero");

        verify(mockedRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar um livro corretamente")
    void deveDeletarLivroPorId() {
        // given
        Livro livro = TestDataFactory.createSampleLivro();
        livro.setPessoasEmprestimo(Collections.emptyList());

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(livro));
        doNothing().when(mockedRepository).deleteById(anyLong());

        // then
        serviceUnderTest.deleteLivroById(livro.getIdLivro());
        verify(mockedRepository).deleteById(anyLong());
        verify(mockedRepository).findById(livro.getIdLivro());
    }

    @Test
    @DisplayName("Deve lançar uma exceção EntityNotFoundException ao tentar deletar um livro inexistente")
    void naoDeveDeletarLivroInexistentePorId() {
        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> serviceUnderTest.deleteLivroById(1L))
                .isInstanceOf(EntityNotFoundException.class);
        assertThatThrownBy(() -> serviceUnderTest.findLivroById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Entidade do tipo Livro com o ID 1 não encontrada");
    }

    @Test
    @DisplayName("Deve lançar uma exceção InvalidOperationException ao tentar deletar um livro emprestado a alguém")
    void naoDeveDeletarLivroEmprestado() {
        // given
        Livro livro = TestDataFactory.createSampleLivro();
        Pessoa pessoa = TestDataFactory.createSamplePessoa();
        livro.setPessoasEmprestimo(Collections.singletonList(pessoa));

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(livro));

        // then
        assertThatThrownBy(() -> serviceUnderTest.deleteLivroById(1L))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessageContaining("Livro não pode estar com nenhuma devolução pendente");

    }

    @Test
    @DisplayName("Deve realizar o retorno do livro")
    void deveRetornarLivroEmprestado(){
        // given
        Livro livro = TestDataFactory.createSampleLivro();
        Pessoa pessoa = TestDataFactory.createSamplePessoa();
        livro.getPessoasEmprestimo().add(pessoa);

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(livro));
        when(mockedPessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        // then
        serviceUnderTest.returnLivro(livro.getIdLivro(), pessoa.getIdPessoa());
        assertThat(livro.getPessoasEmprestimo()).isEmpty();
        assertThat(pessoa.getLivrosEmprestimo()).isEmpty();
        verify(mockedRepository).save(livro);
        verify(mockedPessoaRepository).save(pessoa);
    }

    @Test
    @DisplayName("Deve lançar uma exceção InvalidLoanException por tentar devolver um livro que não foi pego emprestado pela pessoa")
    void naoDeveRetornarLivroNaoEmprestado() {
        // given
        Livro livro = TestDataFactory.createSampleLivro();
        Pessoa pessoa = TestDataFactory.createSamplePessoa();
        Pessoa pessoa1 = TestDataFactory.createSamplePessoa();
        pessoa1.setIdPessoa(2L);
        livro.getPessoasEmprestimo().add(pessoa);

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(livro));
        when(mockedPessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa1));

        // then
        assertThatThrownBy(()->serviceUnderTest.returnLivro(livro.getIdLivro(), pessoa1.getIdPessoa()))
                .isInstanceOf(InvalidLoanException.class);
    }

    @Test
    @DisplayName("Deve pegar um livro emprestado")
    void devePegarLivroEmprestado() {
        // given
        Livro livro = TestDataFactory.createSampleLivro();
        Pessoa pessoa = TestDataFactory.createSamplePessoa();
        int quantityLivro = livro.getQuantidade();

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(livro));
        when(mockedPessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        // then
        serviceUnderTest.borrowLivro(livro.getIdLivro(), pessoa.getIdPessoa());
        verify(mockedRepository).save(any());
        verify(mockedPessoaRepository).save(any());
        assertThat(livro.getQuantidade()).isEqualTo(quantityLivro-1);
        assertThat(livro.getPessoasEmprestimo()).contains(pessoa);
        assertThat(pessoa.getLivrosEmprestimo()).contains(livro);
    }

    @Test
    @DisplayName("Deve lançar uma exceção InvalidQuantityException ao tentar pegar um livro com quantidade menor que 1")
    void naoDevePegarLivroEmprestadoDevidoAQuantidade() {
        // given
        Livro livro = TestDataFactory.createSampleLivro();
        livro.setQuantidade(0);
        Pessoa pessoa = TestDataFactory.createSamplePessoa();
        int quantityLivro = livro.getQuantidade();

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(livro));
        when(mockedPessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        assertThatThrownBy(() -> serviceUnderTest.borrowLivro(livro.getIdLivro(), pessoa.getIdPessoa())).isInstanceOf(InvalidQuantityException.class);
        assertThat(livro.getQuantidade()).isEqualTo(quantityLivro);
    }

    @Test
    @DisplayName("Deve lançar uma exceção InvalidLoanException ao tentar pegar um livro já emprestado")
    void naoDevePegarLivroJaEmprestado() {
        // given
        Livro livro = TestDataFactory.createSampleLivro();
        Pessoa pessoa = TestDataFactory.createSamplePessoa();
        livro.getPessoasEmprestimo().add(pessoa);
        int quantityLivro = livro.getQuantidade();

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(livro));
        when(mockedPessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        // then
        assertThatThrownBy(() -> serviceUnderTest.borrowLivro(livro.getIdLivro(), pessoa.getIdPessoa())).isInstanceOf(InvalidLoanException.class);
        assertThat(livro.getQuantidade()).isEqualTo(quantityLivro);
    }
}