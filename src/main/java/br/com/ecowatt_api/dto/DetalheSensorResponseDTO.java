package br.com.ecowatt_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetalheSensorResponseDTO {
    private Long id;
    private String nomeSensor;
    private String tipoSensor;
    private String status;
    private String produtoConectado;
    private String localizacao;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
}
