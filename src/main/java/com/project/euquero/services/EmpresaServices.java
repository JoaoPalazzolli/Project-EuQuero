package com.project.euquero.services;

import com.project.euquero.controller.EmpresaController;
import com.project.euquero.dtos.EmpresaDTO;
import com.project.euquero.dtos.EnderecoDTO;
import com.project.euquero.execptions.ResourceNotFoundException;
import com.project.euquero.mapper.Mapper;
import com.project.euquero.models.Empresa;
import com.project.euquero.models.Endereco;
import com.project.euquero.models.enums.TipoEmpresa;
import com.project.euquero.repositories.EmpresaRepository;
import com.project.euquero.repositories.UserRepository;
import com.project.euquero.services.auth.authenticated.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EmpresaServices {

    private static final Logger LOGGER = Logger.getLogger(EmpresaServices.class.getName());

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Transactional(readOnly = true)
    public ResponseEntity<List<EmpresaDTO>> findAll(){
        LOGGER.info("Buscando Todas as Empresas");

        var empresasDTO = Mapper.parseListObject(empresaRepository.findAll(), EmpresaDTO.class);

        empresasDTO.forEach(x -> x.add(linkTo(methodOn(EmpresaController.class).findById(x.getId())).withSelfRel()));

        return ResponseEntity.ok(empresasDTO);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<EmpresaDTO> findById(Long empresaId){
        LOGGER.info("Buscando Empresa pelo ID");

        var empresa = empresaRepository.findById(empresaId)
                .orElseThrow(ResourceNotFoundException::new);

        var empresaDTO = Mapper.parseObject(empresa, EmpresaDTO.class);

        empresaDTO.add(linkTo(methodOn(EmpresaController.class).findById(empresaId)).withSelfRel());

        return ResponseEntity.ok(empresaDTO);
    }

    @Transactional
    public ResponseEntity<EmpresaDTO> createEmpresa(EmpresaDTO empresaDTO){
        LOGGER.info("Registrando Empresa");

        var user = AuthenticatedUser.getAuthenticatedUser();

        var empresa = Mapper.parseObject(empresaDTO, Empresa.class);

        empresa.setUrlEmpresa(null);
        empresa.setTipoEmpresa(empresaDTO.getTipoEmpresa().equalsIgnoreCase("ONG") ? TipoEmpresa.ONG : TipoEmpresa.EMPRESA);
        empresa.setUrlImage(null);

        empresaRepository.save(empresa);

        empresa.setEnderecos(
                Mapper.parseListObject(empresa.getEnderecos().stream().map(x -> {
                    return enderecoService.registrarEndereco(Mapper.parseObject(x, EnderecoDTO.class), empresa);
                }).toList(), Endereco.class));

        var dto = Mapper.parseObject(empresa, EmpresaDTO.class);

        dto.add(linkTo(methodOn(EmpresaController.class).findById(dto.getId())).withSelfRel());

        return ResponseEntity.ok(dto);
    }
}
