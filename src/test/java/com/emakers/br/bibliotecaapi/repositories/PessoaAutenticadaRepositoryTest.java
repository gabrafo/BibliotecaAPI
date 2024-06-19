package com.emakers.br.bibliotecaapi.repositories;

import com.emakers.br.bibliotecaapi.domain.dto.request.PessoaAutenticadaRequestDTO;
import com.emakers.br.bibliotecaapi.domain.dto.request.PessoaRequestDTO;
import com.emakers.br.bibliotecaapi.domain.entities.Pessoa;
import com.emakers.br.bibliotecaapi.domain.entities.PessoaAutenticada;
import com.emakers.br.bibliotecaapi.domain.enums.PessoaAutenticadaRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // Perfil de testes
@Tag("unit")
class PessoaAutenticadaRepositoryTest {

    @Autowired
    PessoaAutenticadaRepository pessoaAutenticadaRepository;

    @Autowired
    EntityManager entityManager;

    private Pessoa createPessoa(PessoaRequestDTO pessoaRequest) {
        Pessoa pessoa = new Pessoa(pessoaRequest);
        entityManager.persist(pessoa);
        return pessoa;
    }

    private PessoaAutenticada createPessoaAutenticada(Pessoa pessoa, PessoaAutenticadaRequestDTO pessoaAutenticadaRequest) {
        PessoaAutenticada pessoaAutenticada = new PessoaAutenticada(pessoa, pessoaAutenticadaRequest.nomeDeUsuario(), pessoaAutenticadaRequest.senha(), PessoaAutenticadaRole.USER);
        entityManager.persist(pessoaAutenticada);
        return pessoaAutenticada;
    }

    @Test
    @DisplayName("Deve retornar o usuário autenticado corretamente do BD")
    void findByNomeDeUsuarioCase1() {
        PessoaRequestDTO pessoaRequest = new PessoaRequestDTO("Nome", "CEP", new PessoaAutenticadaRequestDTO("gabrafo", "teste"), PessoaAutenticadaRole.USER);
        Pessoa pessoa = createPessoa(pessoaRequest);
        PessoaAutenticadaRequestDTO pessoaAutenticadaRequest = new PessoaAutenticadaRequestDTO(pessoaRequest.pessoaAutenticadaRequestDTO().nomeDeUsuario(), pessoaRequest.pessoaAutenticadaRequestDTO().senha());
        PessoaAutenticada pessoaAutenticada = createPessoaAutenticada(pessoa, pessoaAutenticadaRequest);

        UserDetails found = pessoaAutenticadaRepository.findByNomeDeUsuario(pessoaAutenticadaRequest.nomeDeUsuario());

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(pessoaAutenticadaRequest.nomeDeUsuario());
    }

    @Test
    @DisplayName("Deve não achar nenhum usuário")
    void findByNomeDeUsuarioCase2() {
        String username = "usuario_inexistente";
        UserDetails found = pessoaAutenticadaRepository.findByNomeDeUsuario(username);
        assertThat(found).isNull(); // UserDetails não é Opcional, por isso checo se retorna null ao invés de se está vazio
    }
}