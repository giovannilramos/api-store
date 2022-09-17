package br.com.quaz.store.services;

import br.com.quaz.store.entities.User;
import br.com.quaz.store.enums.StatusCode;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.helper.UserHelper.setUserEntity;

@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final UserRepository userRepository;

    public void createUser(final UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AlreadyExistsException("Username already exists", StatusCode.ALREADY_EXISTS.getStatusCode());
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AlreadyExistsException("E-mail already exists", StatusCode.ALREADY_EXISTS.getStatusCode());
        }
        final var user = new User();

        setUserEntity(user, userRequest);

        userRepository.save(user);
    }
}
