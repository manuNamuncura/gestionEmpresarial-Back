package com.gestion.getionempresarial.repository;

import com.gestion.getionempresarial.model.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Page<Empleado> findByActivoTrue(Pageable pageable);
    Optional<Empleado> findByEmail(String email);

    Page<Empleado> findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(String nombre, String email, Pageable pageable);
    @Query("SELECT e FROM Empleado e WHERE e.activo = true AND " +
            "(LOWER(e.nombre) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :term, '%')))")
    Page<Empleado> buscarActivosPorTermino(@Param("term") String term, Pageable pageable);

}
