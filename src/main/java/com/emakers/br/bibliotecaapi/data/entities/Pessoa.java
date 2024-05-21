package com.emakers.br.bibliotecaapi.data.entities;

import com.emakers.br.bibliotecaapi.data.dto.request.PessoaRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa", nullable = false)
    private Long idPessoa;

    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private PessoaAutenticada pessoaAutenticada;

    @ManyToMany()
    @JoinTable(
        name = "emprestimo",
        joinColumns = @JoinColumn(name = "id_pessoa"),
        inverseJoinColumns = @JoinColumn(name = "id_livro")
    )
    List<Livro> livrosEmprestimo;

    @Builder
    public Pessoa(PessoaRequestDTO request){
        this.nome = request.nome();
        this.cep = request.cep();
    }
}
