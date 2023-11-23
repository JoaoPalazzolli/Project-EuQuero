package com.project.euquero.repositories;

import com.project.euquero.models.Organizacao;
import com.project.euquero.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {

    @Query("""
            select case when (count(e) > 0) then true else false end
            from Empresa e where e.cnpj = :cnpj
            """)
    Boolean existsCNPJ(@Param("cnpj") String cnpj);

    Boolean existsByNomeAndDescricao(String nome, String descricao);

    @Query(value = """
            select * from organizacao
            inner join user_organizacao on user_organizacao.organizacao_id = organizacao.id
            where user_organizacao.user_id = :userId
            """, nativeQuery = true)
    List<Organizacao> findOrganizacaoByUserId(@Param("userId") Long UserId);

    @Query(value = """
            select * from organizacao
            inner join user_organizacao on user_organizacao.organizacao_id = organizacao.id
            where user_organizacao.user_id = :userId and user_organizacao.organizacao_id = :organizacaoId
            """, nativeQuery = true)
    Optional<Organizacao> findByOrganizacaoIdAndUserId(@Param("organizacaoId") Long organizacaoId, @Param("userId") Long userId);

}
