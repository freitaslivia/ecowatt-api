package br.com.ecowatt_api.controller;

import br.com.ecowatt_api.dto.*;
import br.com.ecowatt_api.model.Usuario;
import br.com.ecowatt_api.repository.UsuarioRepository;
import br.com.ecowatt_api.security.SecurityConfigurations;
import br.com.ecowatt_api.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth-usuario")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/login")
    @Operation(summary = "Login do usuario", description = "Metodo para logar um usuario")
    @ApiResponse(responseCode = "200", description = "usuario logado com sucesso")
    @ApiResponse(responseCode = "400", description = "erro de validção")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthRequestDTO authRequestDTO) {
        // Gerar um token de usuário e senha
        var usuarioSenha = new UsernamePasswordAuthenticationToken(authRequestDTO.login(), authRequestDTO.senha());
        // Autenticar esse token
        var auth = authenticationManager.authenticate(usuarioSenha);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token, ((Usuario) auth.getPrincipal()).getId(), ((Usuario) auth.getPrincipal()).getLogin()));
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastro de usuario", description = "Metodo para cadastrar um usuario")
    @ApiResponse(responseCode = "201", description = "usuario criado com sucesso")
    @ApiResponse(responseCode = "400", description = "erro de validção")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (usuarioRepository.findByLogin(registerDTO.login()) != null) {
            return ResponseEntity.badRequest().body("Já existe um login com esse nome, adicione um login não existente ");
        }
        if (!registerDTO.senha().equals(registerDTO.senhaConfirmacao())) {
            return ResponseEntity.badRequest().body("A senha e senha confirmação devem ser iguais.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.senha());
        Usuario usuario = new Usuario(registerDTO.login(), encryptedPassword, registerDTO.nomeCompleto());
        usuario.setDataCriacao(LocalDateTime.now());
        usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("usuario criado com sucesso.");
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = SecurityConfigurations.SECURITY)
    @Operation(summary = "Busca usuario por id", description = "Metodo para buscar usuario por id")
    @ApiResponse(responseCode = "200", description = "usuario retornado com sucesso")
    @ApiResponse(responseCode = "204", description = "retorna vazio pois não existe nenhum sensor usuario com esse id")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity<UsuarioResponseDTO> read(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Usuario usuarioGet = usuario.get();
            UsuarioResponseDTO responseDTO =  modelMapper.map(usuarioGet, UsuarioResponseDTO.class);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = SecurityConfigurations.SECURITY)
    @Operation(summary = "Deleta usuario por id", description = "Metodo para deletar usuario por id")
    @ApiResponse(responseCode = "200", description = "usuario deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "usuario não encontrado")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = SecurityConfigurations.SECURITY)
    @Operation(summary = "Alterar senha do usuário", description = "Método para alterar a senha do usuário por id")
    @ApiResponse(responseCode = "200", description = "senha alterada com sucesso")
    @ApiResponse(responseCode = "400", description = "erro de validação")
    @ApiResponse(responseCode = "401", description = "senha atual não corresponde")
    @ApiResponse(responseCode = "404", description = "usuário não encontrado")
    @ApiResponse(responseCode = "403", description = "você não tem permissão para alterar a senha deste usuário")
    @ApiResponse(responseCode = "500", description = "erro no servidor")
    public ResponseEntity<String> alterarSenhaPorId(@PathVariable Long id, @RequestBody @Valid AlterarSenhaRequestDTO dto) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        Usuario usuario = usuarioOptional.get();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginAutenticado = auth.getName();

        if (!usuario.getLogin().equals(loginAutenticado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para alterar a senha deste usuário");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(dto.getSenhaAtual(), usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha atual não está correta");
        }
        if(encoder.matches(dto.getSenhaNova(), usuario.getSenha())){
            return ResponseEntity.badRequest().body("A nova senha nova não pode ser igual a anterior");
        }
        if (!dto.getSenhaNova().equals(dto.getSenhaConfirmacao())) {
            return ResponseEntity.badRequest().body("A nova senha e a confirmação de senha devem ser iguais");
        }
        String senhaCriptografada = encoder.encode(dto.getSenhaNova());

        var dataModificacao = LocalDateTime.now();
        usuarioRepository.updateSenhaById(id, senhaCriptografada, dataModificacao);

        return ResponseEntity.ok("Senha alterada com sucesso");
    }

}