package com.project.euquero.controller;

import com.project.euquero.dtos.OrganizacaoDTO;
import com.project.euquero.services.OrganizacaoService;
import com.project.euquero.utils.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizacao")
public class OrganizacaoController {

    @Autowired
    private OrganizacaoService organizacaoService;

    // ajustar mapping para o findById
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<List<OrganizacaoDTO>> findaAll(){
        return organizacaoService.findAll();
    }

    @GetMapping(value = "/premium",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<List<OrganizacaoDTO>> findAllByUser(){
        return organizacaoService.findAllByUser();
    }

    @GetMapping(value = "/{organizacaoId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<OrganizacaoDTO> findById(@PathVariable(value = "organizacaoId") Long organizacaoId){
        return organizacaoService.findById(organizacaoId);
    }

    @PostMapping(
            value = "/premium",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<OrganizacaoDTO> createOrganizacao(
            @RequestBody OrganizacaoDTO organizacaoDTO/*,
            @RequestParam(value = "imagem") MultipartFile file*/) {
        return organizacaoService.createOrganizacao(organizacaoDTO);
    }

    @PutMapping(
            value = "/premium",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ResponseEntity<OrganizacaoDTO> updateOrganizacao(
            @RequestBody OrganizacaoDTO organizacaoDTO/*,
            @RequestParam(value = "imagem") MultipartFile file*/){
        return organizacaoService.updateOrganizacao(organizacaoDTO);
    }
}
