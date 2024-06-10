package com.emakers.br.bibliotecaapi.services;

import com.emakers.br.bibliotecaapi.domain.dto.request.LivroRequestDTO;
import com.emakers.br.bibliotecaapi.domain.dto.response.LivroResponseDTO;
import com.emakers.br.bibliotecaapi.domain.entities.Livro;
import com.emakers.br.bibliotecaapi.domain.entities.Pessoa;
import com.emakers.br.bibliotecaapi.exceptions.general.EntityNotFoundException;
import com.emakers.br.bibliotecaapi.exceptions.general.InvalidOperationException;
import com.emakers.br.bibliotecaapi.exceptions.livro.InvalidLoanException;
import com.emakers.br.bibliotecaapi.exceptions.livro.InvalidQuantityException;
import com.emakers.br.bibliotecaapi.repositories.LivroRepository;
import com.emakers.br.bibliotecaapi.repositories.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<LivroResponseDTO> findAllLivros() {
        List<Livro> livros = livroRepository.findAll();

        return livros.stream().map(LivroResponseDTO::new).collect(Collectors.toList());
    }

    public LivroResponseDTO findLivroById(Long idLivro) {
        Livro livro = findLivroEntityById(idLivro);

        return new LivroResponseDTO(livro);
    }

    public LivroResponseDTO createLivro(LivroRequestDTO livroRequestDTO) {
        if(livroRequestDTO.quantidade()<0) throw new InvalidQuantityException();

        Livro livro = new Livro(livroRequestDTO);
        livroRepository.save(livro);
        return new LivroResponseDTO(livro);
    }

    public LivroResponseDTO updateLivro(Long idLivro, LivroRequestDTO livroRequestDTO) {
        Livro livro = findLivroEntityById(idLivro);

        if(livroRequestDTO.quantidade()<0) throw new InvalidQuantityException();

        livro.setNome(livroRequestDTO.nome());
        livro.setAutor(livroRequestDTO.autor());
        livro.setQuantidade(livroRequestDTO.quantidade());
        livro.setDataLancamento(livroRequestDTO.dataLancamento());
        livroRepository.save(livro);
        return new LivroResponseDTO(livro);
    }

    public String deleteLivroById(Long idLivro) {
        if(!findLivroEntityById(idLivro).getPessoasEmprestimo().isEmpty()) throw new InvalidOperationException("Livro não pode estar com nenhuma devolução pendente");
        livroRepository.deleteById(idLivro);
        return "Livro deletado com sucesso";
    }

    @Transactional
    public String borrowLivro(Long idLivro, Long idPessoa) {
        Livro livro = findLivroEntityById(idLivro);
        Pessoa pessoa = pessoaRepository.findById(idPessoa).orElseThrow(() -> new EntityNotFoundException(idPessoa, "Pessoa"));

        if(livro.getQuantidade()<1) throw new InvalidQuantityException();
        if(livro.getPessoasEmprestimo().contains(pessoa)) throw new InvalidLoanException(pessoa.getNome() + " já está com o livro '" + livro.getNome() + "'");

        livro.setQuantidade(livro.getQuantidade()-1);
        livro.getPessoasEmprestimo().add(pessoa);
        pessoa.getLivrosEmprestimo().add(livro);
        livroRepository.save(livro);
        pessoaRepository.save(pessoa);
        return "Livro emprestado com sucesso";
    }

    @Transactional
    public String returnLivro(Long idLivro, Long idPessoa) {
        Livro livro = findLivroEntityById(idLivro);
        Pessoa pessoa = pessoaRepository.findById(idPessoa).orElseThrow(() -> new EntityNotFoundException(idPessoa, "Pessoa"));

        if(!livro.getPessoasEmprestimo().contains(pessoa)) throw new InvalidLoanException(pessoa.getNome() + " não está com o livro '" + livro.getNome() + "'");

        livro.setQuantidade(livro.getQuantidade()+1);
        livro.getPessoasEmprestimo().remove(pessoa);
        pessoa.getLivrosEmprestimo().remove(livro);
        livroRepository.save(livro);
        pessoaRepository.save(pessoa);

        return "Livro devolvido com sucesso";
    }

    private Livro findLivroEntityById(Long idLivro) {
        return livroRepository.findById(idLivro).orElseThrow(() -> new EntityNotFoundException(idLivro, "Livro"));
    }
}
