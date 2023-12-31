package com.project.euquero.services;

import com.project.euquero.controller.OrganizacaoController;
import com.project.euquero.dtos.OrganizacaoDTO;
import com.project.euquero.dtos.EnderecoDTO;
import com.project.euquero.execptions.ConflictException;
import com.project.euquero.execptions.ResourceNotFoundException;
import com.project.euquero.mapper.Mapper;
import com.project.euquero.models.Empresa;
import com.project.euquero.models.Endereco;
import com.project.euquero.models.Ongs;
import com.project.euquero.models.Organizacao;
import com.project.euquero.repositories.OrganizacaoRepository;
import com.project.euquero.repositories.UserRepository;
import com.project.euquero.services.auth.authenticated.AuthenticatedUser;
import com.project.euquero.utils.ErrorMessages;
import jakarta.persistence.DiscriminatorValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class OrganizacaoService {

    private static final Logger LOGGER = Logger.getLogger(OrganizacaoService.class.getName());

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private AbstractUploadFileService uploadFileService;

    @Transactional(readOnly = true)
    public ResponseEntity<List<OrganizacaoDTO>> findAll(){
        LOGGER.info("Buscando Todas as Organizações");

        var organizacao = organizacaoRepository.findAll();

        var organizacoesDTO = Mapper.parseListObject(organizacao, OrganizacaoDTO.class);

        organizacoesDTO.forEach(x -> {
            organizacao.forEach(y -> {
                if (x.getId().equals(y.getId())){
                    x.setTipo(y.getDecriminatorValue());
                }
            });
        });

        organizacoesDTO.forEach(x -> x.add(linkTo(methodOn(OrganizacaoController.class).findById(x.getId())).withSelfRel()));

        return ResponseEntity.ok(organizacoesDTO);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<OrganizacaoDTO>> findAllByUser(){
        LOGGER.info("Buscando Todas as Organizações");

        var user = AuthenticatedUser.getAuthenticatedUser();

        var organizacoesDTO = Mapper.parseListObject(organizacaoRepository.findOrganizacaoByUserId(user.getId()), OrganizacaoDTO.class);

        organizacoesDTO.forEach(x -> x.add(linkTo(methodOn(OrganizacaoController.class).findById(x.getId())).withSelfRel()));

        return ResponseEntity.ok(organizacoesDTO);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<OrganizacaoDTO> findById(Long organizacaoId){
        LOGGER.info("Buscando Organização pelo ID");

        var organizacao = organizacaoRepository.findById(organizacaoId)
                .orElseThrow(ResourceNotFoundException::new);

        var organizacaoDTO = Mapper.parseObject(organizacao, OrganizacaoDTO.class);

        organizacaoDTO.add(linkTo(methodOn(OrganizacaoController.class).findById(organizacaoId)).withSelfRel());

        return ResponseEntity.ok(organizacaoDTO);
    }

    @Transactional
    public ResponseEntity<OrganizacaoDTO> createOrganizacao(OrganizacaoDTO organizacaoDTO){
        LOGGER.info("Registrando Organização");

        var user = AuthenticatedUser.getAuthenticatedUser();

        if (organizacaoDTO.getCnpj() != null && organizacaoRepository.existsCNPJ(organizacaoDTO.getCnpj())){
            throw new ConflictException(ErrorMessages.THIS_CNPJ_ALREADY_EXISTS);
        }

        var tipo = organizacaoDTO.getCnpj() == null ? Ongs.class : Empresa.class;

        var organizacao = Mapper.parseObject(organizacaoDTO, tipo);

        organizacao.setUrlImage(null);

        // organizacao.setUrlImage(uploadFileService.uploadFile(file));

        organizacaoRepository.save(organizacao);

        user.getOrganizacoes().add(organizacao);

        userRepository.save(user);

        organizacao.setEnderecos(
                Mapper.parseListObject(organizacao.getEnderecos().stream().map(x ->
                        enderecoService.registrarEndereco(Mapper.parseObject(x, EnderecoDTO.class), organizacao)
                ).toList(), Endereco.class));

        var dto = Mapper.parseObject(organizacao, OrganizacaoDTO.class);

        dto.add(linkTo(methodOn(OrganizacaoController.class).findById(dto.getId())).withSelfRel());

        LOGGER.info("Organização Registrada com Sucesso");

        return ResponseEntity.ok(dto);
    }

    @Transactional
    public ResponseEntity<OrganizacaoDTO> updateOrganizacao(OrganizacaoDTO organizacaoDTO) {
        // ajustar --- precisa pegar o discriminator value -- verificar se o discriminator value bate com as info

        var user = AuthenticatedUser.getAuthenticatedUser();

        var organizacaoAntiga = organizacaoRepository.findByOrganizacaoIdAndUserId(organizacaoDTO.getId(), user.getId())
                .orElseThrow(ResourceNotFoundException::new);

        var tipo = !organizacaoAntiga.getDecriminatorValue().equalsIgnoreCase("EMPRESA") ? Ongs.class : Empresa.class;

        // verificar com o id do user tbm
        if (organizacaoRepository.existsByNomeAndDescricao(organizacaoDTO.getNome(), organizacaoDTO.getDescricao()) &&
            !organizacaoAntiga.getNome().equals(organizacaoDTO.getNome())) {
            throw new ConflictException(ErrorMessages.CONTENT_CONFLICT);
        }

        var novaOrganizacao = Mapper.parseObject(organizacaoDTO, tipo);

        novaOrganizacao.setEnderecos(organizacaoAntiga.getEnderecos());

        /*
        if (file != null)
            novaOrganizacao.setUrlImage(uploadFileService.uploadFile(file));
        else
            novaOrganizacao.setUrlImage(organizacaoAntiga.getUrlImage());
         */
        novaOrganizacao.setUrlImage(organizacaoAntiga.getUrlImage());

        var novaOrganizacaoDTO = Mapper.parseObject(organizacaoRepository.save(novaOrganizacao), OrganizacaoDTO.class);

        return ResponseEntity.ok(novaOrganizacaoDTO);
    }
}
