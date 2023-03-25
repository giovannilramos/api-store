package br.com.quaz.store.services.user;

import br.com.quaz.store.controllers.response.UserResponse;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.controllers.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;
import static br.com.quaz.store.services.converters.UserConverter.convertUserDTOToEntity;
import static br.com.quaz.store.services.converters.UserConverter.convertUserDTOToResponse;
import static br.com.quaz.store.services.converters.UserConverter.convertUserEntityToDTO;
import static br.com.quaz.store.services.converters.UserConverter.convertUserRequestToDTO;

@Service
@AllArgsConstructor
public class UpdateUserService {
    private final UserRepository userRepository;

    public UserResponse updateUser(final String jwtToken, final UpdateUserRequest updateUserRequest) {
        final var email = decoderTokenJwt(jwtToken);
        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        final var userDTO = convertUserEntityToDTO(user);
        if (!userDTO.getEmail().equals(updateUserRequest.getEmail()) && Boolean.TRUE.equals(userRepository.existsByEmail(updateUserRequest.getEmail()))) {
            throw new AlreadyExistsException("E-mail already exists");
        }
        if (!userDTO.getUsername().equals(updateUserRequest.getUsername()) && Boolean.TRUE.equals(userRepository.existsByUsername(updateUserRequest.getUsername()))) {
            throw new AlreadyExistsException("Username already exists");
        }

        userRepository.save(convertUserDTOToEntity(convertUserRequestToDTO(updateUserRequest, userDTO)));

        return convertUserDTOToResponse(convertUserEntityToDTO(user));
    }
}
