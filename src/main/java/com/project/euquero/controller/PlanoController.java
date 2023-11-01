package com.project.euquero.controller;

import com.project.euquero.services.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plans")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @PostMapping(value = "/buy/{permission_id}")
    public ResponseEntity<?> comprarPlano(@PathVariable(value = "permission_id") Long permissionId) throws Exception{
        return planoService.comprarPlano(permissionId);
    }
}
