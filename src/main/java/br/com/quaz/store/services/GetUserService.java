package br.com.quaz.store.services;

import br.com.quaz.store.enums.StatusCode;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserService {
    private final UserRepository userRepository;

    public UserResponse findUserById(final UUID uuid) {
        final var user  = userRepository.findById(uuid).orElseThrow(() -> new NotFoundException("User not found", StatusCode.NOT_FOUND.getStatusCode()));
        final var userResponse = new UserResponse();

        userResponse.setUuid(user.getUuid());
        userResponse.setName(user.getName());
        userResponse.setBirthDate(user.getBirthDate());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());

        return userResponse;
    }
}
