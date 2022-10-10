package br.com.quaz.store.configs.security;

import br.com.quaz.store.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    @Value("${jwt.expiration}")
    private Integer expTime;
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        final var principal = (UserDetails) authentication.getPrincipal();
        final var user = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        final var token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli() + expTime))
                .sign(Algorithm.HMAC256(secret));
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
}
