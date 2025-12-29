package com.gestion.getionempresarial.controller;

import com.gestion.getionempresarial.model.Departamento;
import com.gestion.getionempresarial.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public List<Departamento> listar () {
        return departamentoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Departamento> crear(@RequestBody Departamento departamento) {
        return ResponseEntity.ok(departamentoService.guardar(departamento));
    }
}
