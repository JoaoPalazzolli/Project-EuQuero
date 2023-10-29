package com.project.euquero.security.jwt.service;

import com.project.euquero.dtos.auth.TokenDTO;
import com.project.euquero.execptions.InvalidJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long expireLength;

    public String extractEmail(String token){
        return extractClaim(Claims::getSubject, token);
    }

    public Boolean isTokenValid(UserDetails userDetails, String token){
        String email = extractEmail(token);
        return (userDetails.getUsername().equals(email)) && !isTokenExpirated(token);
    }

    public TokenDTO refreshToken(Map<String, Objects> extraClaims, String refreshToken, UserDetails userDetails){

        if (!isTokenValid(userDetails, refreshToken)) {
            throw new InvalidJwtAuthenticationException();
        }

        return createToken(extraClaims, userDetails);
    }

    public TokenDTO refreshToken(String refreshToken, UserDetails userDetails) {
        return refreshToken(new HashMap<>(), refreshToken, userDetails);
    }

    public TokenDTO createToken(UserDetails userDetails){
        return createToken(new HashMap<>(), userDetails);
    }

    public TokenDTO createToken(Map<String, Objects> extraClaims, UserDetails userDetails){
        return TokenDTO.builder()
                .email(userDetails.getUsername())
                .authenticated(true)
                .created(new Date())
                .expiration(new Date(System.currentTimeMillis() + (expireLength * 3)))
                .accessToken(generateToken(extraClaims, userDetails))
                .refreshToken(generateRefreshToken(extraClaims, userDetails))
                .build();
    }

    private String generateToken(Map<String, Objects> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (expireLength * 3)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private String generateRefreshToken(Map<String, Objects> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (expireLength * 6)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Boolean isTokenExpirated(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(Claims::getExpiration, token);
    }

    private <T> T extractClaim(Function<Claims, T> claimsResolver, String token){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
