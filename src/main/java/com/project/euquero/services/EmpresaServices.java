package com.project.euquero.services;

import com.project.euquero.dtos.EmpresaDTO;
import com.project.euquero.mapper.Mapper;
import com.project.euquero.models.Empresa;
import com.project.euquero.models.enums.TipoEmpresa;
import com.project.euquero.repositories.EmpresaRepository;
import com.project.euquero.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class EmpresaServices {

    private static final Logger LOGGER = Logger.getLogger(EmpresaServices.class.getName());

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<EmpresaDTO>> findAll(){
        LOGGER.info("Buscando Todas as Empresas");

        var empresasDTO = Mapper.parseListObject(empresaRepository.findAll(), EmpresaDTO.class);

        return ResponseEntity.ok(empresasDTO);
    }

    public ResponseEntity<EmpresaDTO> create(EmpresaDTO empresaDTO){
        LOGGER.info("Criando uma Empresa");

        var empresa = Mapper.parseObject(empresaDTO, Empresa.class);

        empresa.setUrlEmpresa(null);
        empresa.setTipoEmpresa(empresaDTO.getTipoEmpresa().equalsIgnoreCase("ONG") ? TipoEmpresa.ONG : TipoEmpresa.EMPRESA);
        empresa.setUrlImage(null);

        var dto = Mapper.parseObject(empresaRepository.save(empresa), EmpresaDTO.class);

        return ResponseEntity.ok(dto);
    }
}
