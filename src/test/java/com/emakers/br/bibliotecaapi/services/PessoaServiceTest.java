package com.emakers.br.bibliotecaapi.services;

import com.emakers.br.bibliotecaapi.domain.dto.request.PessoaAutenticadaRequestDTO;
import com.emakers.br.bibliotecaapi.domain.dto.request.PessoaRequestDTO;
import com.emakers.br.bibliotecaapi.domain.dto.response.PessoaResponseDTO;
import com.emakers.br.bibliotecaapi.domain.entities.Livro;
import com.emakers.br.bibliotecaapi.domain.entities.Pessoa;
import com.emakers.br.bibliotecaapi.domain.enums.PessoaAutenticadaRole;
import com.emakers.br.bibliotecaapi.exceptions.general.EntityNotFoundException;
import com.emakers.br.bibliotecaapi.exceptions.general.InvalidOperationException;
import com.emakers.br.bibliotecaapi.repositories.PessoaRepository;
import com.emakers.br.bibliotecaapi.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepository mockedRepository;

    @InjectMocks
    private PessoaService serviceUnderTest;

    @Test
    @DisplayName("Deve encontrar todas as pessoas")
    void deveEncontrarTodasAsPessoas() {
        // when
        serviceUnderTest.findAllPessoas();

        // then
        verify(mockedRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia de pessoas")
    void deveEncontrarListaVaziaDePessoas() {
        // when
        when(mockedRepository.findAll()).thenReturn(List.of());

        // then
        List<PessoaResponseDTO> result = serviceUnderTest.findAllPessoas();
        verify(mockedRepository).findAll();
        verifyNoMoreInteractions(mockedRepository);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Deve encontrar pessoa com ID válido")
    void deveEncontrarPessoaPorId() {
        // given
        Pessoa p = TestDataFactory.createSamplePessoa();

        // when
        when(mockedRepository.findById(p.getIdPessoa())).thenReturn(Optional.of(p));
        PessoaResponseDTO result = serviceUnderTest.findPessoaById(p.getIdPessoa());

        // then
        assertThat(result)
                .hasFieldOrPropertyWithValue("id", p.getIdPessoa())
                .hasFieldOrPropertyWithValue("nome", p.getNome())
                .hasFieldOrPropertyWithValue("cep", p.getCep());
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar encontrar um ID sem ninguém")
    void naoDeveEncontrarPessoaComIdInexistente() {
        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> serviceUnderTest.findPessoaById(anyLong())).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    @DisplayName("Deve atualizar os valores de nome e CEP da pessoa")
    void deveAtualizarPessoa() {
        // given
        Pessoa p = TestDataFactory.createSamplePessoa();

        // Credenciais não são atualizadas nesse update, então não serão testadas
        PessoaAutenticadaRequestDTO credentialsRequest = new PessoaAutenticadaRequestDTO("nome_de_usuario", "1234");

        PessoaRequestDTO request = new PessoaRequestDTO("Ciclano", "CEP_ATUALIZADO",
                credentialsRequest, PessoaAutenticadaRole.USER);

        // when
        when(mockedRepository.findById(p.getIdPessoa())).thenReturn(Optional.of(p));
        serviceUnderTest.updatePessoa(p.getIdPessoa(), request);

        // then
        assertThat(p.getNome()).isEqualTo(request.nome());
        assertThat(p.getCep()).isEqualTo(request.cep());
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar atualizar uma pessoa com ID inexistente")
    void naoDeveAtualizarPessoaInexistente() {
        // given
        PessoaAutenticadaRequestDTO credentialsRequest = new PessoaAutenticadaRequestDTO("nome_de_usuario", "1234");

        PessoaRequestDTO request = new PessoaRequestDTO("Ciclano", "CEP_ATUALIZADO",
                credentialsRequest, PessoaAutenticadaRole.USER);

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> serviceUnderTest.updatePessoa(1L, request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Entidade do tipo Pessoa com o ID 1 não encontrada");
    }

    @Test
    @DisplayName("Deve excluir pessoa")
    void deveExcluirPessoa() {
        // given
        Pessoa p = TestDataFactory.createSamplePessoa();

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(p));
        doNothing().when(mockedRepository).deleteById(1L);

        // then
        serviceUnderTest.deletePessoaById(p.getIdPessoa());
        verify(mockedRepository).deleteById(1L);
        verify(mockedRepository).findById(p.getIdPessoa());
    }

    @Test
    @DisplayName("Não deve excluir pessoa com ID inexistente")
    void naoDeveExcluirPessoaComIdInexistente() {
        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> serviceUnderTest.deletePessoaById(anyLong()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Deve lançar InvalidOperationException ao tentar excluir pessoa com devolução pendente")
    void naoDeveExcluirPessoaComDevolucaoPendente(){
        // given
        Pessoa p = TestDataFactory.createSamplePessoa();
        Livro l = TestDataFactory.createSampleLivro();
        p.getLivrosEmprestimo().add(l);

        // when
        when(mockedRepository.findById(anyLong())).thenReturn(Optional.of(p));

        // then
        assertThatThrownBy(() -> serviceUnderTest.deletePessoaById(p.getIdPessoa()))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessageContaining("Pessoa não pode estar com nenhuma devolução pendente");
        verify(mockedRepository).findById(p.getIdPessoa());
    }
}