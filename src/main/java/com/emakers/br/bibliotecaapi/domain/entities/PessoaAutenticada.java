package com.emakers.br.bibliotecaapi.domain.entities;

import com.emakers.br.bibliotecaapi.domain.enums.PessoaAutenticadaRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "pessoa_autenticada")
public class PessoaAutenticada implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPessoaAutenticada;

    @OneToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    @Column(name = "nome_de_usuario", unique = true, nullable = false)
    private String nomeDeUsuario;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private PessoaAutenticadaRole role;

    public PessoaAutenticada(Pessoa pessoa, String nomeDeUsuario, String senha, PessoaAutenticadaRole role){
        this.pessoa = pessoa;
        this.nomeDeUsuario = nomeDeUsuario;
        this.senha = senha;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == PessoaAutenticadaRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nomeDeUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
