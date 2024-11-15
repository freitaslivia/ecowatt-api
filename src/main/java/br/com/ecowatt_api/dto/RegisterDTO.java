package br.com.ecowatt_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(

            @NotEmpty(message = "O campo login é obrigatório")
            @Size(max = 150)
            @Schema(description = "login", example = "teste")
            String login,
            @NotEmpty(message = "O campo senha é obrigatório")
            @Schema(description = "senha", example = "Teste123@")
            @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,250}$")
            String senha,
            @NotEmpty(message = "O campo nome completo é obrigatório")
            @Size(max =250)
            @Schema(description = "nome completo", example = "Livia Freitas Ferreira")
            String nomeCompleto,
            @Schema(description = "senha confirmação", example = "Teste123@")
            @NotEmpty(message = "O campo senha confirmações é obrigatório")
            String senhaConfirmacao) {}
