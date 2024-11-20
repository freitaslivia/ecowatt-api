package br.com.ecowatt_api.repository;

import br.com.ecowatt_api.dto.RegisterDTO;
import br.com.ecowatt_api.model.Usuario;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Retorna com sucesso o login")
    void findByLoginSucess() {
        String login = "bruna";
        RegisterDTO dto = new RegisterDTO(login, "Teste123@", "Bruna Freitas", "Teste123@");
        this.createUser(dto);

        Optional<UserDetails> result = Optional.ofNullable(this.usuarioRepository.findByLogin(login));

        assertThat(result.isPresent()).isTrue();
    }

    private Usuario createUser(RegisterDTO dto){
        Usuario newUser = new Usuario(dto);
        newUser.setDataCriacao(LocalDateTime.now());
        this.entityManager.persist(newUser);
        return newUser;
    }
}