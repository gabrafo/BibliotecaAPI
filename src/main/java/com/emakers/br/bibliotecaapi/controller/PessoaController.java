package com.emakers.br.bibliotecaapi.controller;

import com.emakers.br.bibliotecaapi.data.dto.request.PessoaRequestDTO;
import com.emakers.br.bibliotecaapi.data.dto.response.PessoaResponseDTO;
import com.emakers.br.bibliotecaapi.services.PessoaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
@Tag(name = "Pessoa", description = "Endpoints relacionados a Pessoa")
public class PessoaController {
    
    @Autowired
    private PessoaService pessoaService;

    @GetMapping(value="/all",
            produces = "application/json")
    public ResponseEntity<List<PessoaResponseDTO>> getAllPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findAllPessoas());
    }

    @GetMapping(value="/{idPessoa}",
            produces = "application/json")
    public ResponseEntity<PessoaResponseDTO> getPessoaById(@PathVariable("idPessoa") Long idPessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findPessoaById(idPessoa));
    }

    @PostMapping(value = "/create",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<PessoaResponseDTO> createPessoa(@Valid @RequestBody PessoaRequestDTO pessoaRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.createPessoa(pessoaRequestDTO));
    }

    @PutMapping(value = "/update/{idPessoa}",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(@PathVariable Long idPessoa,
                                                              @Valid @RequestBody PessoaRequestDTO pessoaRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.updatePessoa(idPessoa, pessoaRequestDTO));
    }

    @DeleteMapping(value = "/delete/{idPessoa}")
    public ResponseEntity<String> deletePessoa(@PathVariable Long idPessoa){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.deletePessoaById(idPessoa));
    }
}
