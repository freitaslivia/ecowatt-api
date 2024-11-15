package br.com.ecowatt_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Table(name = "ecowatt_sensores")
@Entity
@Getter
@Setter
public class Sensores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id_sensor", columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "ds_tipo_sensor", columnDefinition = "VARCHAR(50)", nullable = false)
    private String tipoSensor;

    @Column( name = "ds_local_instalacao", columnDefinition = "VARCHAR(100)", nullable = true)
    private String localizacao;

    @Column(name = "ds_status", columnDefinition = "VARCHAR(20)", nullable = true)
    private String status;

    @Column( name = "ds_nome_sensor", columnDefinition = "VARCHAR(100)", nullable = false)
    private String nomeSensor;

    @Column( name = "ds_descricao", columnDefinition = "VARCHAR(500)", nullable = true)
    private String descricao;

    @Column(name = "ds_produto_conectado", columnDefinition = "VARCHAR(100)", nullable = false)
    private String produtoConectado;

    @Column(name = "nr_sinal", columnDefinition = "INT(11)", nullable = true)
    private int sinal;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;
    @Column(nullable = true)
    private LocalDateTime dataModificacao;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Usuario usuario;
}
