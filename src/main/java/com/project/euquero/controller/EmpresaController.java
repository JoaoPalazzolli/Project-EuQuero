package com.project.euquero.controller;

import com.project.euquero.dtos.EmpresaDTO;
import com.project.euquero.services.EmpresaServices;
import com.project.euquero.utils.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaServices empresaServices;

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<List<EmpresaDTO>> findAll(){
        return empresaServices.findAll();
    }

    @GetMapping(value = "/{empresaId}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<EmpresaDTO> findById(@PathVariable(value = "empresaId") Long empresaId){
        return empresaServices.findById(empresaId);
    }

    @PostMapping(
            value = "/premium",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<EmpresaDTO> createEmpresa(@RequestBody EmpresaDTO empresaDTO){
        return empresaServices.createEmpresa(empresaDTO);
    }
}
