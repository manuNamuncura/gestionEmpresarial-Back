package com.gestion.getionempresarial.controller;

import com.gestion.getionempresarial.model.Empleado;
import com.gestion.getionempresarial.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// EmpleadoController.java
@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Empleado>> getAll(Pageable pageable) {
        // Solo devolvemos los activos
        return ResponseEntity.ok(service.listarActivos(pageable));
    }

    @PostMapping
    public ResponseEntity<Empleado> create(@RequestBody Empleado empleado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardarOActivar(empleado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.borradoLogico(id);
        return ResponseEntity.noContent().build();
    }
}
