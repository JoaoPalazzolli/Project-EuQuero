package com.project.euquero.repositories;

import com.project.euquero.models.User;
import com.project.euquero.models.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByAccessToken(String accessToken);
    Optional<Token> findByRefreshToken(String refreshToken);

    @Query("""
            select t from Token t inner join User u on t.user.id = u.id
            where u.id = :userId and (t.expired = false or t.revoked = false)
            """)
    List<Token>  findAllValidTokensByUser(@Param("userId") Long userId);

    @Query("""
            select t from Token t inner join User u on t.user.id = u.id
            where u.id = :userId and (t.expired = true or t.revoked = true)
            """)
    List<Token> findAllInvalidTokensByUser(@Param("userId") Long userId);
}
