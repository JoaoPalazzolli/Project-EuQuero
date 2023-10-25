package com.project.euquero.controller;

import com.project.euquero.dtos.EmpresaDTO;
import com.project.euquero.services.EmpresaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaServices empresaServices;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EmpresaDTO>> findAll(){
        return empresaServices.findAll();
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<EmpresaDTO> create(@RequestBody EmpresaDTO empresaDTO){
        return empresaServices.create(empresaDTO);
    }
}
