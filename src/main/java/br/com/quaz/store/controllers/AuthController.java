package br.com.quaz.store.controllers;

import br.com.quaz.store.config.secutiry.TokenGenerator;
import br.com.quaz.store.dto.TokenDTO;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.request.LoginRequest;
import br.com.quaz.store.request.UserRequest;
import br.com.quaz.store.services.CreateUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final CreateUserService createUserService;
    private final TokenGenerator tokenGenerator;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public AuthController(CreateUserService createUserService, TokenGenerator tokenGenerator, DaoAuthenticationProvider daoAuthenticationProvider, @Qualifier("jwtAuthenticationProvider") JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.createUserService = createUserService;
        this.tokenGenerator = tokenGenerator;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody UserRequest userRequest) {
        final var user = new User(userRequest.getName(), userRequest.getUsername(), userRequest.getPassword(), userRequest.getBirthDate(), userRequest.getRoles());
        createUserService.createUser(user);

        final var authentication = UsernamePasswordAuthenticationToken.authenticated(user, userRequest.getPassword(), userRequest.getRoles());
        return ResponseEntity.status(HttpStatus.OK).body(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest loginRequest) {
        final var authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword()));

        return ResponseEntity.status(HttpStatus.OK).body(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDTO> token(@RequestBody TokenDTO tokenDTO) {
        final var authentication = jwtAuthenticationProvider.authenticate(new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));

        return ResponseEntity.status(HttpStatus.OK).body(tokenGenerator.createToken(authentication));
    }
}
