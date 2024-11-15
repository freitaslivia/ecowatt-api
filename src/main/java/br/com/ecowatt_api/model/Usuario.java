package br.com.ecowatt_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "ecowatt_usuario")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", columnDefinition = "INT(11)")
    private Long id;
    @Column(name = "ds_usuario", columnDefinition = "VARCHAR(150)", nullable = false)
    private String login;
    @Column(name = "ds_nome_completo", columnDefinition = "VARCHAR(250)", nullable = false)
    private String nomeCompleto;
    @Column(name = "ds_senha", columnDefinition = "VARCHAR(250)", nullable = false)
    private String senha;
    @Column(nullable = false)
    private LocalDateTime dataCriacao;
    @Column(nullable = true)
    private LocalDateTime dataModificacao;


    public Usuario(String login, String encryptedPassword, String nomeCompleto) {
        this.login = login;
        this.senha = encryptedPassword;
        this.nomeCompleto = nomeCompleto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
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
