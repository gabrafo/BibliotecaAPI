package com.emakers.br.bibliotecaapi.controller;

import com.emakers.br.bibliotecaapi.data.dto.request.PessoaRequestDTO;
import com.emakers.br.bibliotecaapi.data.entities.Pessoa;
import com.emakers.br.bibliotecaapi.infra.security.TokenService;
import com.emakers.br.bibliotecaapi.data.dto.request.PessoaAutenticadaRequestDTO;
import com.emakers.br.bibliotecaapi.data.dto.response.PessoaAutenticadaResponseDTO;
import com.emakers.br.bibliotecaapi.data.entities.PessoaAutenticada;
import com.emakers.br.bibliotecaapi.repository.PessoaAutenticadaRepository;
import com.emakers.br.bibliotecaapi.repository.PessoaRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pessoa/auth")
@Tag(name = "Pessoa Autenticada", description = "Endpoints relacionados a autenticação")
public class PessoaAutenticadaController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PessoaAutenticadaRepository authRepository;
    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid PessoaAutenticadaRequestDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.nomeDeUsuario(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((PessoaAutenticada) auth.getPrincipal());

        return ResponseEntity.ok(new PessoaAutenticadaResponseDTO(token));
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid PessoaRequestDTO data){
        if(this.authRepository.findByNomeDeUsuario(data.pessoaAutenticadaRequestDTO().nomeDeUsuario()) != null) return ResponseEntity.badRequest().build();

        Pessoa newPessoa = new Pessoa(data);

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.pessoaAutenticadaRequestDTO().senha());
        PessoaAutenticada newPessoaAutenticada = new PessoaAutenticada(newPessoa, data.pessoaAutenticadaRequestDTO().nomeDeUsuario(), encryptedPassword, data.role());

        newPessoaAutenticada.setPessoa(newPessoa);

        this.pessoaRepository.save(newPessoa);
        this.authRepository.save(newPessoaAutenticada);

        return ResponseEntity.ok().build();
    }

}