package br.com.quaz.store.services;

import br.com.quaz.store.enums.StatusCode;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateUserService {
    private final UserRepository userRepository;

    public void updateUser(final UUID uuid, final UpdateUserRequest updateUserRequest) {
        final var user = userRepository.findById(uuid).orElseThrow(() -> new NotFoundException("User not found", StatusCode.NOT_FOUND.getStatusCode()));

        if (userRepository.existsByUsername(updateUserRequest.getUsername())) {
            throw new AlreadyExistsException("Username already exists", StatusCode.ALREADY_EXISTS.getStatusCode());
        }
        if (userRepository.existsByEmail(updateUserRequest.getEmail())) {
            throw new AlreadyExistsException("E-mail already exists", StatusCode.ALREADY_EXISTS.getStatusCode());
        }

        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());
        user.setUsername(updateUserRequest.getUsername());
        user.setBirthDate(updateUserRequest.getBirthDate());

        userRepository.save(user);
    }
}
