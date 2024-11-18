package br.com.ecowatt_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarSenhaRequestDTO {
    @NotEmpty
    @Schema(description = "senha", example = "Teste123@")
    private String senhaAtual;
    @NotEmpty
    @Schema(description = "senha", example = "Teste123@@")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,250}$")
    private String senhaNova;
    @NotEmpty
    @Schema(description = "senha", example = "Teste123@@")
    private String senhaConfirmacao;
}
