package br.com.quaz.store.config.security;

import br.com.quaz.store.config.security.service.UserDetailsServiceImpl;
import br.com.quaz.store.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler {
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    @Value("${jwt.expiration}")
    private Integer expTime;
    @Value("${jwt.secret}")
    private String secret;

    @SneakyThrows
    public String generateToken(final User userDetails) {
        final var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return JWT.create()
                .withIssuer("API Store")
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli() + expTime))
                .sign(Algorithm.HMAC256(secret));
    }

    public UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {
        final var token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(token) || !token.startsWith(TOKEN_PREFIX)) {
            return null;
        }
        final var email = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("API Store")
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
        if (Objects.isNull(email) || email.isEmpty()) {
            return null;
        }
        final var userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }
}
