package br.com.quaz.store.configs.security.filters;

import br.com.quaz.store.configs.security.service.UserDetailsServiceImpl;
import br.com.quaz.store.utils.ApiException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsServiceImpl userDetailsService;
    private final String secret;

    public JwtAuthorizationFilter(final AuthenticationManager authenticationManager, final UserDetailsServiceImpl userDetailsService, final String secret) {
        super(authenticationManager);
        this.secret = secret;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken auth = null;
        try {
            auth = getAuthentication(request);

        } catch (Exception e) {
            final var statusCode = HttpStatus.valueOf(401);
            final var responseStream = response.getOutputStream();
            final var mapper = new ObjectMapper();
            final var exception = new ApiException();

            exception.setMessage("Token expired, log in again");
            exception.setHttpStatus(statusCode);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(responseStream, exception);
            responseStream.flush();
        }
        if (Objects.isNull(auth)) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {
        final var token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(token) || !token.startsWith(TOKEN_PREFIX)) {
            return null;
        }
        final var email = JWT.require(Algorithm.HMAC256(secret))
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
