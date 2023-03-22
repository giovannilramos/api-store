package br.com.quaz.store.config.security.filters;

import br.com.quaz.store.config.security.AuthSuccessHandler;
import br.com.quaz.store.utils.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final AuthSuccessHandler authSuccessHandler;
    @Override
    @SneakyThrows
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) {
        UsernamePasswordAuthenticationToken auth = null;
        try {
            auth = authSuccessHandler.getAuthentication(request);
        } catch (final Exception e) {
            final var responseStream = response.getOutputStream();
            final var mapper = new ObjectMapper();
            final var exception = ExceptionResponse.builder()
                    .message("Token expired, log in again").build();

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
}
