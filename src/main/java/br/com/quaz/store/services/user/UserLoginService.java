package br.com.quaz.store.services.user;

import br.com.quaz.store.config.security.AuthSuccessHandler;
import br.com.quaz.store.controllers.request.LoginRequest;
import br.com.quaz.store.controllers.response.TokenJwtResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.services.converters.TokenConverter.convertToTokenDTO;
import static br.com.quaz.store.services.converters.TokenConverter.convertTokenDTOToTokenResponse;

@Service
@AllArgsConstructor
public class UserLoginService {
    private final AuthenticationManager authenticationManager;
    private final AuthSuccessHandler authSuccessHandler;

    public TokenJwtResponse userLogin(final LoginRequest loginRequest) {
        final var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getIdentification(), loginRequest.getPassword());
        final var authentication = authenticationManager.authenticate(authenticationToken);
        final var tokenJwt = authSuccessHandler.generateToken((User) authentication.getPrincipal());

        return convertTokenDTOToTokenResponse(convertToTokenDTO(tokenJwt));
    }
}
