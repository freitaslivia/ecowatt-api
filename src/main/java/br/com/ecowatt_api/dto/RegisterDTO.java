package br.com.ecowatt_api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO(
            @NotEmpty(message = "O campo login é obrigatório")
            String login,
            @NotEmpty(message = "O campo senha é obrigatório")
            @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$")
            String senha,
            @NotEmpty(message = "O campo nome completo é obrigatório")
            String nomeCompleto,
            @NotEmpty(message = "O campo senha confirmações é obrigatório")
            String senhaConfirmacao) {}
