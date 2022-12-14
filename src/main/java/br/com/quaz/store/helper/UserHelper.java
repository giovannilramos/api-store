package br.com.quaz.store.helper;

import br.com.quaz.store.exceptions.UnauthorizedException;
import br.com.quaz.store.services.dto.JwtPayloadDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class UserHelper {
    public static String decoderTokenJwt(final String jwtToken) {
        final JwtPayloadDTO identification;
        try {
            final var token = jwtToken.replace("Bearer ", "");
            final var chunks = token.split("\\.");
            final var payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
            identification = new ObjectMapper().readValue(payload, JwtPayloadDTO.class);
        } catch (final Exception e) {
            throw new UnauthorizedException("User unauthorized");
        }

        return identification.getSub();
    }
}
