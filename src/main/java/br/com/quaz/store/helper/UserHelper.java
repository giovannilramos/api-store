package br.com.quaz.store.helper;

import br.com.quaz.store.dto.JwtPayload;
import br.com.quaz.store.entities.Address;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.RolesRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

public class UserHelper {
    public static void setUserEntity(final UserRequest userRequest,
                                     final RolesRepository rolesRepository,
                                     final UserRepository userRepository,
                                     final AddressRepository addressRepository) {
        final var user = new User();

        final var roles = rolesRepository.findAllByUuidIn(userRequest.getRolesUuid());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        user.setBirthDate(userRequest.getBirthDate());
        user.setRoles(roles);

        userRepository.save(user);

        final var address = new Address();

        final var userRegistered = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() ->
                        new NotFoundException("User not found"));

        address.setCep(userRequest.getCep());
        address.setNumber(userRequest.getNumber());
        address.setStreet(userRequest.getStreet());
        address.setDistrict(userRequest.getDistrict());
        address.setCountry(userRequest.getCountry());
        address.setCity(userRequest.getCity());
        address.setState(userRequest.getState());
        address.setComplement(userRequest.getComplement());
        address.setUser(userRegistered);

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
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return identification.getSub();
    }
}
