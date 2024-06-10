package com.emakers.br.bibliotecaapi.domain.entities;

import com.emakers.br.bibliotecaapi.domain.dto.request.LivroRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    List<Pessoa> pessoasEmprestimo = new ArrayList<>();;

    @Builder
    public Livro(LivroRequestDTO request){
        this.nome = request.nome();
        this.autor = request.autor();
        this.quantidade = request.quantidade();
        this.dataLancamento = request.dataLancamento();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(getIdLivro(), livro.getIdLivro()) && Objects.equals(getNome(), livro.getNome()) && Objects.equals(getAutor(), livro.getAutor()) && Objects.equals(getQuantidade(), livro.getQuantidade()) && Objects.equals(getDataLancamento(), livro.getDataLancamento()) && Objects.equals(getPessoasEmprestimo(), livro.getPessoasEmprestimo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdLivro(), getNome(), getAutor(), getQuantidade(), getDataLancamento(), getPessoasEmprestimo());
    }
}
