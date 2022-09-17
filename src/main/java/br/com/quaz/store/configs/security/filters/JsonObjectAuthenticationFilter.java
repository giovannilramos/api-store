package br.com.quaz.store.configs.security.filters;

import br.com.quaz.store.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final var buffer = request.getReader();
            final var sb = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            final var authRequest = objectMapper.readValue(sb.toString(), LoginRequest.class);
            final var token = new UsernamePasswordAuthenticationToken(authRequest.getIdentification(), authRequest.getPassword());

            setDetails(request, token);

            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}