package br.com.quaz.store.helper;

import br.com.quaz.store.dto.JwtPayload;
import br.com.quaz.store.entities.Address;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.exceptions.UnauthorizedException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.RolesRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserHelper {
    public static void setUserEntity(final UserRequest userRequest,
                                     final RolesRepository rolesRepository,
                                     final UserRepository userRepository,
                                     final AddressRepository addressRepository) {
        final var roles = rolesRepository.findAllByUuidIn(userRequest.getRolesUuid());

        final var user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .birthDate(userRequest.getBirthDate())
                .roles(roles)
                .build();

        userRepository.save(user);

        final var userRegistered = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() ->
                        new NotFoundException("User not found"));

        final var address = Address.builder()
                .cep(userRequest.getCep())
                .number(userRequest.getNumber())
                .street(userRequest.getStreet())
                .district(userRequest.getDistrict())
                .country(userRequest.getCountry())
                .city(userRequest.getCity())
                .state(userRequest.getState())
                .complement(userRequest.getComplement())
                .user(userRegistered).build();

        addressRepository.save(address);
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
