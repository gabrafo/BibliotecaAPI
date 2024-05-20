package com.emakers.br.bibliotecaapi.controller;

import com.emakers.br.bibliotecaapi.data.dto.request.LivroRequestDTO;
import com.emakers.br.bibliotecaapi.data.dto.request.LoanRequestDTO;
import com.emakers.br.bibliotecaapi.data.dto.response.LivroResponseDTO;
import com.emakers.br.bibliotecaapi.services.LivroService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
@Tag(name = "livro", description = "Endpoints relacionados a Livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping(value="/all",
            produces = "application/json")
    public ResponseEntity<List<LivroResponseDTO>> getAllLivros() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findAllLivros());
    }

    @GetMapping(value="/{idLivro}",
            produces = "application/json")
    public ResponseEntity<LivroResponseDTO> getLivroById(@PathVariable("idLivro") Long idLivro) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findLivroById(idLivro));
    }

    @PostMapping(value = "/create",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<LivroResponseDTO> createLivro(@Valid @RequestBody LivroRequestDTO livroRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.createLivro(livroRequestDTO));
    }

    @PutMapping(value = "/update/{idLivro}",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<LivroResponseDTO> updateLivro(@PathVariable Long idLivro,
                                                          @Valid @RequestBody LivroRequestDTO livroRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.updateLivro(idLivro, livroRequestDTO));
    }

    @DeleteMapping(value = "/delete/{idLivro}")
    public ResponseEntity<String> deleteLivro(@PathVariable Long idLivro){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.deleteLivroById(idLivro));
    }

    @PostMapping(value = "/borrow", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> borrowLivro(@RequestBody LoanRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.borrowLivro(requestDTO.idLivro(), requestDTO.idPessoa()));
    }

    @PostMapping(value = "/return", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> returnLivro(@RequestBody LoanRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.returnLivro(requestDTO.idLivro(), requestDTO.idPessoa()));
    }
}
