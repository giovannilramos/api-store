package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.request.LoginRequest;
import br.com.quaz.store.controllers.response.TokenJwtResponse;
import br.com.quaz.store.services.user.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserLoginService userLogin;

    @PostMapping
    public ResponseEntity<TokenJwtResponse> login(@RequestBody @Valid final LoginRequest loginRequest) {
        return ResponseEntity.ok(userLogin.userLogin(loginRequest));
    }
}
