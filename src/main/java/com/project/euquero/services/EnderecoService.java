package com.project.euquero.services;

import com.project.euquero.dtos.EnderecoDTO;
import com.project.euquero.execptions.ConflictException;
import com.project.euquero.mapper.Mapper;
import com.project.euquero.models.Empresa;
import com.project.euquero.models.Endereco;
import com.project.euquero.models.Organizacao;
import com.project.euquero.repositories.EnderecoRepository;
import com.project.euquero.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
public class EnderecoService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public EnderecoDTO registrarEndereco(EnderecoDTO enderecoDTO, Organizacao organizacao){

        var dto = consultarCEP(enderecoDTO);

        if (enderecoRepository.existsByOrganizacaoIdAndCepAndNumeroAndLocalidadeAndUfAndLogradouroAndComplementoAndBairro(
                organizacao.getId(),
                dto.getCep(),
                dto.getNumero(),
                dto.getLocalidade(),
                dto.getUf(),
                dto.getLogradouro(),
                dto.getComplemento(),
                dto.getBairro()))
            throw new ConflictException(ErrorMessages.ADDRESS_CONFLICT);

        var endereco = Mapper.parseObject(dto, Endereco.class);
        endereco.setOrganizacao(organizacao);

        return Mapper.parseObject(enderecoRepository.save(endereco), EnderecoDTO.class);
    }

    private EnderecoDTO consultarCEP(EnderecoDTO enderecoDTO){
        ResponseEntity<EnderecoDTO> resp =
                restTemplate.getForEntity("https://viacep.com.br/ws/" + enderecoDTO.getCep() + "/json/", EnderecoDTO.class);

        if (resp.getBody().getBairro().isBlank())
            resp.getBody().setBairro(enderecoDTO.getBairro());

        if (resp.getBody().getLogradouro().isBlank())
            resp.getBody().setLogradouro(enderecoDTO.getLogradouro());

        resp.getBody().setNumero(enderecoDTO.getNumero());
        resp.getBody().setComplemento(enderecoDTO.getComplemento());

        return resp.getBody();
    }



}
