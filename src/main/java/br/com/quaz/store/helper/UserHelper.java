package br.com.quaz.store.helper;

import br.com.quaz.store.dto.JwtPayload;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.exceptions.UnauthorizedException;
import br.com.quaz.store.repositories.RolesRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class UserHelper {
    public static void setUserEntity(final UserRequest userRequest,
                                     final RolesRepository rolesRepository,
                                     final UserRepository userRepository) {
        final var roles = rolesRepository.findAllByUuidIn(userRequest.getRolesUuid());

        userRepository.save(User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .birthDate(userRequest.getBirthDate())
                .roles(roles)
                .build());
    }

    public static String decoderTokenJwt(final String jwtToken) {
        final var token = jwtToken.replace("Bearer ", "");
        final var chunks = token.split("\\.");
        final var decoder = Base64.getUrlDecoder();
        final var payload = new String(decoder.decode(chunks[1]));
        final JwtPayload identification;
        try {
            identification = new ObjectMapper().readValue(payload, JwtPayload.class);
        } catch (final JsonProcessingException e) {
            throw new UnauthorizedException("User unauthorized");
        }

        return identification.getSub();
    }
}
