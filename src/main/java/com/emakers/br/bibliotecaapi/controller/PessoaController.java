package com.emakers.br.bibliotecaapi.controller;

import com.emakers.br.bibliotecaapi.data.dto.request.PessoaRequestDTO;
import com.emakers.br.bibliotecaapi.data.dto.response.PessoaResponseDTO;
import com.emakers.br.bibliotecaapi.services.PessoaService;
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
@RequestMapping("/pessoa")
@Tag(name = "Pessoa", description = "Endpoints relacionados a Pessoa")
public class PessoaController {
    
    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Lista as pessoas",
            description = "Lista todas as pessoas do Banco de Dados.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping(value="/all",
            produces = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<PessoaResponseDTO>> getAllPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findAllPessoas());
    }

    @Operation(summary = "Busca uma pessoa por ID",
            description = "Busca uma pessoa no Banco de Dados usando o ID como parâmetro.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping(value="/{idPessoa}",
            produces = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<PessoaResponseDTO> getPessoaById(@PathVariable("idPessoa") Long idPessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findPessoaById(idPessoa));
    }

    @Operation(summary = "Atualiza uma pessoa",
            description = "Atualiza alguma das pessoas do Banco de Dados.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PutMapping(value = "/update/{idPessoa}",
            produces = "application/json",
            consumes = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(@PathVariable Long idPessoa,
                                                              @Valid @RequestBody PessoaRequestDTO pessoaRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.updatePessoa(idPessoa, pessoaRequestDTO));
    }

    @Operation(summary = "Exclui uma pessoa",
            description = "Exclui uma das pessoas do Banco de Dados.",
            tags = {"Pessoa"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @DeleteMapping(value = "/delete/{idPessoa}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> deletePessoa(@PathVariable Long idPessoa){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.deletePessoaById(idPessoa));
    }
}
