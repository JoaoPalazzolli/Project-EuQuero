package com.project.euquero.services.auth;

import com.project.euquero.dtos.auth.LoginRequestDTO;
import com.project.euquero.dtos.auth.RegisterRequestDTO;
import com.project.euquero.dtos.auth.TokenDTO;
import com.project.euquero.execptionsHandler.InvalidJwtAuthenticationException;
import com.project.euquero.models.UserPermission;
import com.project.euquero.models.UserPermissionPK;
import com.project.euquero.models.User;
import com.project.euquero.models.token.Token;
import com.project.euquero.models.token.enums.TokenType;
import com.project.euquero.repositories.PermissionRepository;
import com.project.euquero.repositories.TokenRepository;
import com.project.euquero.repositories.UserPermissionRepository;
import com.project.euquero.repositories.UserRepository;
import com.project.euquero.security.jwt.service.JwtService;
import com.project.euquero.services.auth.authenticated.AuthenticatedUser;
import com.project.euquero.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class AuthService {

    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<TokenDTO> register(RegisterRequestDTO request){
        LOGGER.info("Registrando Usuario");

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .cpf(request.getCpf())
                .phone(request.getPhone())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true) // false
                .build();

        userRepository.save(user);

        var userPermission = UserPermission.builder()
                .id(UserPermissionPK.builder()
                        .permission(permissionRepository.findByDescricao("USER").orElseThrow())
                        .user(user)
                        .build())
                .expireAt(null)
                .build();

        userPermissionRepository.save(userPermission);

        var tokenDTO = jwtService.createToken(user);

        saveUserToken(tokenDTO, user);

        LOGGER.info("Usuario Registrado com Sucesso");

        return ResponseEntity.ok(tokenDTO);
    }

    @Transactional
    public ResponseEntity<TokenDTO> login(LoginRequestDTO request){
        LOGGER.info("Efetuando Login do Usuario");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

            var tokenDTO = jwtService.createToken(user);

            revokeAllUsersTokens(user);
            saveUserToken(tokenDTO, user);

            LOGGER.info("Login Efetuado com Sucesso");

            return ResponseEntity.ok(tokenDTO);

        } catch (BadCredentialsException e){
            throw new BadCredentialsException(ErrorMessages.BAD_CREDENTIALS);
        }
    }

    @Transactional
    public ResponseEntity<TokenDTO> refreshToken(String refreshToken){
        LOGGER.info("Atualizando Token do Usuario");

        if (refreshToken.startsWith("Bearer ")){
            refreshToken = refreshToken.substring("Bearer ".length());
        }

        var user = AuthenticatedUser.getAuthenticatedUser();

        var tokenDTO = jwtService.refreshToken(refreshToken, user);

        var token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(InvalidJwtAuthenticationException::new);

        token.setAccessToken(tokenDTO.getAccessToken());
        token.setRefreshToken(tokenDTO.getRefreshToken());

        tokenRepository.save(token);

        LOGGER.info("Token do Usuario Atualizado com Sucesso");

        return ResponseEntity.ok(tokenDTO);
    }

    private void revokeAllUsersTokens(User user){
        var validTokens = tokenRepository.findAllValidTokensByUser(user.getId());

        validTokens.forEach(x -> {
            x.setExpired(true);
            x.setRevoked(true);
        });

        tokenRepository.saveAll(validTokens);

        var invalidTokens = tokenRepository.findAllInvalidTokensByUser(user.getId());

        if (invalidTokens.size() >= 5)
            tokenRepository.deleteAll(invalidTokens);

    }

    private void saveUserToken(TokenDTO tokenDTO, User user){
        var token = Token.builder()
                .accessToken(tokenDTO.getAccessToken())
                .refreshToken(tokenDTO.getRefreshToken())
                .tokenType(TokenType.BEARER)
                .createAt(LocalDateTime.now())
                .expired(false)
                .revoked(false)
                .verified(user.getEnabled())
                .user(user)
                .build();

        tokenRepository.save(token);
    }
}
