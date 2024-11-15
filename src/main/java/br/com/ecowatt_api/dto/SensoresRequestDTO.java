package br.com.ecowatt_api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensoresRequestDTO {

    @NotEmpty
    @Size(max = 50)
    private String tipoSensor;
    @NotEmpty
    @Size(max = 20)
    private String status;
    @NotEmpty
    @Size(max = 100)
    private String nomeSensor;
    @NotEmpty
    @Size(max = 100)
    private String produtoConectado;
    //campo opcional
    @Size(max = 500)
    private String descricao;
    //campo opcional
    @Size(max = 100)
    private String localizacao;

    private Long usuarioId;
}
