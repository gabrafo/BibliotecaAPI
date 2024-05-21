package com.emakers.br.bibliotecaapi.services;

import com.emakers.br.bibliotecaapi.data.dto.request.PessoaRequestDTO;
import com.emakers.br.bibliotecaapi.data.dto.response.PessoaResponseDTO;
import com.emakers.br.bibliotecaapi.data.entities.Pessoa;
import com.emakers.br.bibliotecaapi.exceptions.general.EntityNotFoundException;
import com.emakers.br.bibliotecaapi.exceptions.general.InvalidOperationException;
import com.emakers.br.bibliotecaapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PessoaResponseDTO> findAllPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        return pessoas.stream().map(PessoaResponseDTO::new).collect(Collectors.toList());
    }

    public PessoaResponseDTO findPessoaById(Long idPessoa) {
        Pessoa pessoa = findPessoaEntityById(idPessoa);
        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO updatePessoa(Long idPessoa, PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = findPessoaEntityById(idPessoa);

        pessoa.setNome(pessoaRequestDTO.nome());
        pessoa.setCep(pessoaRequestDTO.cep());
        pessoaRepository.save(pessoa);
        return new PessoaResponseDTO(pessoa);
    }

    public String deletePessoaById(Long idPessoa) {
        if(!findPessoaEntityById(idPessoa).getLivrosEmprestimo().isEmpty()) throw new InvalidOperationException("Pessoa não pode estar com nenhuma devolução pendente");
        pessoaRepository.deleteById(idPessoa);
        return "Pessoa removida com sucesso";
    }

    private Pessoa findPessoaEntityById(Long idPessoa) {
        return pessoaRepository.findById(idPessoa).orElseThrow(() -> new EntityNotFoundException(idPessoa, "Pessoa"));
    }
}
