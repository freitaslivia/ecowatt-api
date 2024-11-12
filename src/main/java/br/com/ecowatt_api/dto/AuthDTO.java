package br.com.ecowatt_api.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthDTO(
        @NotEmpty(message = "O campo login é obrigatório")
        String login,
        @NotEmpty(message = "O campo é obrigatório")
        String senha) {}