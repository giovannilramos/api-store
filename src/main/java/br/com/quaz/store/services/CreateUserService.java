package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.repositories.RolesRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.services.converters.UserConverter.convertUserDTOToEntity;
import static br.com.quaz.store.services.converters.UserConverter.convertUserRequestToDTO;

@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    public void createUser(final UserRequest userRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userRequest.getUsername()))) {
            throw new AlreadyExistsException("Username already exists");
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(userRequest.getEmail()))) {
            throw new AlreadyExistsException("E-mail already exists");
        }
        final var roles = rolesRepository.findAllByUuidIn(userRequest.getRolesUuid());

        userRepository.save(convertUserDTOToEntity(convertUserRequestToDTO(userRequest, roles)));
    }
}
