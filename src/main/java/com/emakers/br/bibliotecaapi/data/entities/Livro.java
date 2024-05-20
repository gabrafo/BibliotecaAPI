package com.emakers.br.bibliotecaapi.data.entities;

import com.emakers.br.bibliotecaapi.data.dto.request.LivroRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivro;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Column(name = "autor", nullable = false, length = 45)
    private String autor;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "data_lancamento", nullable = false)
    private Date dataLancamento;

    @ManyToMany(mappedBy = "livrosEmprestimo")
    List<Pessoa> pessoasEmprestimo;

    @Builder
    public Livro(LivroRequestDTO request){
        this.nome = request.nome();
        this.autor = request.autor();
        this.quantidade = request.quantidade();
        this.dataLancamento = request.dataLancamento();
    }
}
