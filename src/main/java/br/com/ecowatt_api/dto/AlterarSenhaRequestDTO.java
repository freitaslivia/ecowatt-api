package br.com.ecowatt_api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarSenhaRequestDTO {
    @NotEmpty
    private String senhaAtual;
    @NotEmpty
    private String senhaNova;
    @NotEmpty
    private String senhaConfirmacao;
}
