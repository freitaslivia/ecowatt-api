package br.com.ecowatt_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record AuthRequestDTO(
        @NotEmpty(message = "O campo login é obrigatório")
        @Schema(description = "login", example = "teste")
        String login,
        @NotEmpty(message = "O campo é obrigatório")
        @Schema(description = "senha", example = "Teste123@")
        String senha) {}