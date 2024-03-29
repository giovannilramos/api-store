package br.com.quaz.store.services.user;

import br.com.quaz.store.controllers.request.UserRequest;
import br.com.quaz.store.controllers.response.UserResponse;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.RolesRepository;
import br.com.quaz.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.services.converters.UserConverter.convertUserDTOToEntity;
import static br.com.quaz.store.services.converters.UserConverter.convertUserDTOToResponse;
import static br.com.quaz.store.services.converters.UserConverter.convertUserEntityToDTO;
import static br.com.quaz.store.services.converters.UserConverter.convertUserRequestToDTO;

@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    public UserResponse createUser(final UserRequest userRequest) {
        final var roles = rolesRepository.findAllByUuidIn(userRequest.getRolesUuid());

        if (roles.isEmpty()) {
            throw new NotFoundException("Roles not found");
        }

        if (Boolean.TRUE.equals(userRepository.existsByUsername(userRequest.getUsername()))) {
            throw new AlreadyExistsException("Username already exists");
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(userRequest.getEmail()))) {
            throw new AlreadyExistsException("E-mail already exists");
        }

        final var user = userRepository.save(convertUserDTOToEntity(convertUserRequestToDTO(userRequest, roles)));
        return convertUserDTOToResponse(convertUserEntityToDTO(user));
    }
}
