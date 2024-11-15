package br.com.ecowatt_api.repository;

import br.com.ecowatt_api.model.Sensores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensoresRepository extends JpaRepository<Sensores, Long> {
    Page<Sensores> findByUsuarioId(Long usuarioId, Pageable pageable);

}
