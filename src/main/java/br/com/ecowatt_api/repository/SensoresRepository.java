package br.com.ecowatt_api.repository;

import br.com.ecowatt_api.model.Sensores;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface SensoresRepository extends JpaRepository<Sensores, Long> {
    Page<Sensores> findByUsuarioId(Long usuarioId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Sensores s SET s.tipoSensor = :tipoSensor, s.status = :status, s.nomeSensor = :nomeSensor, s.produtoConectado = :produtoConectado, s.dataModificacao = :dataModificacao WHERE s.id = :id")
    int updateSensorById(@Param("id") Long idSensor,
                         @Param("tipoSensor") String tipoSensor,
                         @Param("status") String status,
                         @Param("nomeSensor") String nomeSensor,
                         @Param("produtoConectado") String produtoConectado,
                         @Param("dataModificacao") LocalDateTime dataModificacao);

}
