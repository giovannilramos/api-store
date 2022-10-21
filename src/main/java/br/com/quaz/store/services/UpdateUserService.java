package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.controllers.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;
import static br.com.quaz.store.services.converters.UserConverter.convertUserDTOToEntity;
import static br.com.quaz.store.services.converters.UserConverter.convertUserEntityToDTO;
import static br.com.quaz.store.services.converters.UserConverter.convertUserRequestToDTO;

@Service
@AllArgsConstructor
public class UpdateUserService {
    private final UserRepository userRepository;

    public void updateUser(final String jwtToken, final UpdateUserRequest updateUserRequest) {
        final var email = decoderTokenJwt(jwtToken);
        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getEmail().equals(updateUserRequest.getEmail()) && Boolean.TRUE.equals(userRepository.existsByEmail(updateUserRequest.getEmail()))) {
            throw new AlreadyExistsException("E-mail already exists");
        }
        if (!user.getUsername().equals(updateUserRequest.getUsername()) && Boolean.TRUE.equals(userRepository.existsByUsername(updateUserRequest.getUsername()))) {
            throw new AlreadyExistsException("Username already exists");
        }
        final var userDTO = convertUserEntityToDTO(user);
        userRepository.save(convertUserDTOToEntity(convertUserRequestToDTO(updateUserRequest, userDTO)));
    }
}
