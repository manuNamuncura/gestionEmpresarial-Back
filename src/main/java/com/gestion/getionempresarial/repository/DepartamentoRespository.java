package com.gestion.getionempresarial.repository;

import com.gestion.getionempresarial.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRespository extends JpaRepository<Departamento, Long> {
}
