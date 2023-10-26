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
public class EmpresaDTO extends RepresentationModel<EmpresaDTO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String cnpj;
    private String descricao;
    private String tipoEmpresa;
    private String urlEmpresa;
    private String urlImagem;
    private List<EnderecoDTO> enderecos;

}
