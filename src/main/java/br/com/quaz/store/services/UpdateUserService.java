package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;

@Service
@AllArgsConstructor
public class UpdateUserService {
    private final UserRepository userRepository;

    public void updateUser(final String jwtToken, final UpdateUserRequest updateUserRequest) {
        final var sub = decoderTokenJwt(jwtToken);
        var userOptional = userRepository.findByEmail(sub);

        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(sub);
            if (userOptional.isEmpty()) {
                throw new NotFoundException("User not found");
            }
        }

        final var user = userOptional.get();

        if (!user.getEmail().equals(updateUserRequest.getEmail())) {
            if (userRepository.existsByEmail(updateUserRequest.getEmail())) {
                throw new AlreadyExistsException("E-mail already exists");
            }
        }
        if (!user.getUsername().equals(updateUserRequest.getUsername())) {
            if (userRepository.existsByUsername(updateUserRequest.getUsername())) {
                throw new AlreadyExistsException("Username already exists");
            }
        }

        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());
        user.setUsername(updateUserRequest.getUsername());
        user.setBirthDate(updateUserRequest.getBirthDate());

        userRepository.save(user);
    }
}
