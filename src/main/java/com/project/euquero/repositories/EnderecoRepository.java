package com.project.euquero.repositories;

import com.project.euquero.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Boolean existsByOrganizacaoIdAndCepAndNumeroAndLocalidadeAndUfAndLogradouroAndComplementoAndBairro(Long organizacaoId,
                                                                                     String cep, String numero,
                                                                                     String localidade, String uf,
                                                                                     String logradouro, String complemento,
                                                                                     String bairro);
}
