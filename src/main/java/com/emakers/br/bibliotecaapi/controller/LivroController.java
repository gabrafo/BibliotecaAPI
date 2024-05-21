package com.emakers.br.bibliotecaapi.controller;

import com.emakers.br.bibliotecaapi.data.dto.request.LivroRequestDTO;
import com.emakers.br.bibliotecaapi.data.dto.request.EmprestimoRequestDTO;
import com.emakers.br.bibliotecaapi.data.dto.response.LivroResponseDTO;
import com.emakers.br.bibliotecaapi.services.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
@Tag(name = "Livro", description = "Endpoints relacionados a Livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Operation(summary = "Lista os livros",
            description = "Lista todos os livros do Banco de Dados.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping(value="/all",
            produces = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<LivroResponseDTO>> getAllLivros() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findAllLivros());
    }

    @Operation(summary = "Busca um livro por ID",
            description = "Busca um livro no Banco de Dados usando o ID como parâmetro.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping(value="/{idLivro}",
            produces = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<LivroResponseDTO> getLivroById(@PathVariable("idLivro") Long idLivro) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findLivroById(idLivro));
    }

    @Operation(summary = "Cria um livro",
            description = "Cria um novo livro no Banco de Dados.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping(value = "/create",
            produces = "application/json",
            consumes = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<LivroResponseDTO> createLivro(@Valid @RequestBody LivroRequestDTO livroRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.createLivro(livroRequestDTO));
    }

    @Operation(summary = "Atualiza um livro",
            description = "Atualiza algum dos livros do Banco de Dados.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PutMapping(value = "/update/{idLivro}",
            produces = "application/json",
            consumes = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<LivroResponseDTO> updateLivro(@PathVariable Long idLivro,
                                                          @Valid @RequestBody LivroRequestDTO livroRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.updateLivro(idLivro, livroRequestDTO));
    }

    @Operation(summary = "Exclui um livro",
            description = "Exclui um dos livros do Banco de Dados.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @DeleteMapping(value = "/delete/{idLivro}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> deleteLivro(@PathVariable Long idLivro){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.deleteLivroById(idLivro));
    }

    @Operation(summary = "Indica um empréstimo de livro",
            description = "Realiza um empréstimo de um livro, ligando uma Pessoa a um Livro no Banco de Dados.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping(value = "/borrow", produces = "application/json", consumes = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> borrowLivro(@RequestBody EmprestimoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.borrowLivro(requestDTO.idLivro(), requestDTO.idPessoa()));
    }

    @Operation(summary = "Faz um retorno de um livro emprestado",
            description = "Realiza uma devolução de um livro emprestado, encerrando a ligação entre uma Pessoa e esse Livro no Banco de Dados.",
            tags = {"Livro"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LivroResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping(value = "/return", produces = "application/json", consumes = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> returnLivro(@RequestBody EmprestimoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.returnLivro(requestDTO.idLivro(), requestDTO.idPessoa()));
    }
}
