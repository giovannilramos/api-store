package br.com.quaz.store.controllers;

import br.com.quaz.store.config.security.AuthSuccessHandler;
import br.com.quaz.store.controllers.request.LoginRequest;
import br.com.quaz.store.controllers.response.TokenJwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final AuthSuccessHandler authSuccessHandler;

    @PostMapping
    public ResponseEntity<TokenJwtResponse> login(@RequestBody @Valid final LoginRequest loginRequest) {
        final var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getIdentification(), loginRequest.getPassword());
        final var authentication = authenticationManager.authenticate(authenticationToken);
        final var tokenJwt = authSuccessHandler.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(TokenJwtResponse.builder().token(tokenJwt).build());
    }
}
