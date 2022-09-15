package br.com.quaz.store.config.secutiry;

import br.com.quaz.store.entities.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        final var user = new User();
        user.setId(jwt.getSubject());
        return new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
    }
}
