package br.com.quaz.store.helper;

import br.com.quaz.store.dto.JwtPayload;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.repositories.RolesRepository;
import br.com.quaz.store.request.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

public class UserHelper {
    public static void setUserEntity(final User user, final UserRequest userRequest, final RolesRepository rolesRepository) {
        final var roles = rolesRepository.findAllByUuidIn(userRequest.getRolesUuid());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        user.setBirthDate(userRequest.getBirthDate());
        user.setRoles(roles);
    }

    public static String decoderTokenJwt(final String jwtToken) {
        final var token = jwtToken.replace("Bearer ", "");
        final var chunks = token.split("\\.");
        final var decoder = Base64.getUrlDecoder();
        final var payload = new String(decoder.decode(chunks[1]));
        final JwtPayload identification;
        try {
            identification = new ObjectMapper().readValue(payload, JwtPayload.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return identification.getSub();
    }
}
