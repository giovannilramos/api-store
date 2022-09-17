package br.com.quaz.store.services;

import br.com.quaz.store.enums.StatusCode;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.exceptions.UnauthorizedExistsException;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserService {
    private final UserRepository userRepository;

    public UserResponse findLoggedUser(final String identification) {
        var userOptional = userRepository.findByEmail(identification);
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(identification);
            if (userOptional.isEmpty()) {
                throw new NotFoundException("User not found", StatusCode.NOT_FOUND.getStatusCode());
            }
        }
        final var user = userOptional.get();
        final var userResponse = new UserResponse();

        userResponse.setUuid(user.getUuid());
        userResponse.setName(user.getName());
        userResponse.setBirthDate(user.getBirthDate());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());

        return userResponse;
    }
}
