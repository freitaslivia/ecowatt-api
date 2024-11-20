package br.com.ecowatt_api.repository;

import br.com.ecowatt_api.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.senha = :senha, u.dataModificacao = :dataModificacao WHERE u.id = :id")
    int updateSenhaById(@Param("id") Long id, @Param("senha") String senha, @Param("dataModificacao") LocalDateTime dataModificacao);

}