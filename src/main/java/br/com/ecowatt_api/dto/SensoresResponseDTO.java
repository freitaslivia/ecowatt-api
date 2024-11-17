package br.com.ecowatt_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensoresResponseDTO{
    private Long id;
    private String nomeSensor;
    private String tipoSensor;
    private String status;
    private String produtoConectado;
    private String localizacao;
    private String descricao;
}
