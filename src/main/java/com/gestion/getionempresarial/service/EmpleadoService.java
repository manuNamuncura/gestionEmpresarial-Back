package com.gestion.getionempresarial.service;

import com.gestion.getionempresarial.model.Empleado;
import com.gestion.getionempresarial.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Page<Empleado> listarActivos(Pageable pageable) {
        return empleadoRepository.findByActivoTrue(pageable);
    }

    public Empleado guardar(Empleado empleado) {
        if (empleadoRepository.findByEmail(empleado.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        return empleadoRepository.save(empleado);
    }

    public void eliminarLogico(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        empleado.setActivo(false);
        empleadoRepository.save(empleado);
    }
}
