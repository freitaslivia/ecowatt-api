package br.com.ecowatt_api.model;

import br.com.ecowatt_api.dto.RegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

    public Usuario(Long id, String login, String nomeCompleto, LocalDateTime dataCriacao, String senha) {
        this.id = id;
        this.login = login;
        this.nomeCompleto = nomeCompleto;
        this.dataCriacao = dataCriacao;
        this.senha = senha;
    }

    public Usuario(RegisterDTO dto) {
        this.login = dto.login();
        this.senha = dto.senha();
        this.nomeCompleto = dto.nomeCompleto();
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
