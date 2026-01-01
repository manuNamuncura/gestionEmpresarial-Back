package com.gestion.getionempresarial.service;

import com.gestion.getionempresarial.model.Empleado;
import com.gestion.getionempresarial.repository.EmpleadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

// EmpleadoService.java
@Service
public class EmpleadoService {
    private final EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    public Page<Empleado> listarActivos(Pageable pageable) {
        return repository.findByActivoTrue(pageable);
    }

    @Transactional
    public Empleado guardarOActivar(Empleado nuevo) {
        return repository.findByEmail(nuevo.getEmail())
                .map(existente -> {
                    if (Boolean.FALSE.equals(existente.getActivo())) {
                        // Resurrección: El empleado estaba borrado, lo actualizamos y activamos
                        actualizarDatos(existente, nuevo);
                        existente.setActivo(true);
                        return repository.save(existente);
                    }
                    // Si está activo y ya existe, lanzamos error (regla de negocio)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está en uso por un empleado activo.");
                })
                .orElseGet(() -> {
                    // Registro nuevo normal
                    nuevo.setActivo(true);
                    return repository.save(nuevo);
                });
    }

    @Transactional
    public void borradoLogico(Long id) {
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        empleado.setActivo(false);
        repository.save(empleado);
    }

    private void actualizarDatos(Empleado antiguo, Empleado nuevo) {
        antiguo.setNombre(nuevo.getNombre());
        antiguo.setTelefono(nuevo.getTelefono());
        antiguo.setSalario(nuevo.getSalario());
        antiguo.setFechaIngreso(nuevo.getFechaIngreso());
        antiguo.setDepartamento(nuevo.getDepartamento());
    }

    @Transactional(readOnly = true)
    public Page<Empleado> buscarEmpleados(String term, Pageable pageable) {
        // Es mejor crear un método en el Repository que combine (Nombre OR Email) AND Activo = true
        return repository.buscarActivosPorTermino(term, pageable);
    }

    @Transactional
    public Empleado actualizarEmpleado(Long id, Empleado detalles) {
        Empleado empleadoExistente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado por ID: " + id));
        repository.findByEmail(detalles.getEmail()).ifPresent(e -> {
            if (!e.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está siendo utilizado por otro empleado.");

            }
        });

        actualizarDatos(empleadoExistente, detalles);
        return repository.save(empleadoExistente);
    }
}
