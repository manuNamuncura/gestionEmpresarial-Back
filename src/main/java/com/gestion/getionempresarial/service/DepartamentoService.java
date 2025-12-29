package com.gestion.getionempresarial.service;

import com.gestion.getionempresarial.model.Departamento;
import com.gestion.getionempresarial.repository.DepartamentoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRespository departamentoRespository;

    public List<Departamento> listarTodos() {
        return departamentoRespository.findAll();
    }

    public Departamento guardar (Departamento departamento) {
        return departamentoRespository.save(departamento);
    }
}
