package br.com.ecowatt_api.controller;

import br.com.ecowatt_api.dto.AuthDTO;
import br.com.ecowatt_api.dto.LoginResponseDTO;
import br.com.ecowatt_api.dto.RegisterDTO;
import br.com.ecowatt_api.model.Usuario;
import br.com.ecowatt_api.repository.UsuarioRepository;
import br.com.ecowatt_api.security.SecurityConfigurations;
import br.com.ecowatt_api.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth-usuario")
@SecurityRequirement(name = SecurityConfigurations.SECURITY)
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Login do usuario", description = "Metodo para logar um usuario")
    @ApiResponse(responseCode = "200", description = "usuario logado com sucesso")
    @ApiResponse(responseCode = "400", description = "erro de validção")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity login(@RequestBody @Valid AuthDTO authDTO) {
        // Gerar um token de usuário e senha
        var usuarioSenha = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.senha());
        // Autenticar esse token
        var auth = authenticationManager.authenticate(usuarioSenha);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastro de usuario", description = "Metodo para cadastrar um usuario")
    @ApiResponse(responseCode = "201", description = "usuario criado com sucesso")
    @ApiResponse(responseCode = "400", description = "erro de validção")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (usuarioRepository.findByLogin(registerDTO.login()) != null) {
            return ResponseEntity.badRequest().body("Já existe um login com esse nome, adicione um login não existente ");
        }
        if (!registerDTO.senha().equals(registerDTO.senhaConfirmacao())) {
            return ResponseEntity.badRequest().body("A senha e senha confirmação devem ser iguais.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.senha());
        Usuario usuario = new Usuario(registerDTO.login(), encryptedPassword, registerDTO.nomeCompleto());
        usuarioRepository.save(usuario);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}