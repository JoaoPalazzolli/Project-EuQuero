package com.project.euquero.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizacaoDTO extends RepresentationModel<OrganizacaoDTO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String cnpj;
    private String descricao;
    private String objetivo;
    private String urlSite;
    private String urlImagem;
    private List<EnderecoDTO> enderecos;

}
