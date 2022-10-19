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
        final var user = userRepository.findByEmail(sub)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getEmail().equals(updateUserRequest.getEmail()) && Boolean.TRUE.equals(userRepository.existsByEmail(updateUserRequest.getEmail()))) {
            throw new AlreadyExistsException("E-mail already exists");
        }
        if (!user.getUsername().equals(updateUserRequest.getUsername()) && Boolean.TRUE.equals(userRepository.existsByUsername(updateUserRequest.getUsername()))) {
            throw new AlreadyExistsException("Username already exists");
        }

        userRepository.save(user.toBuilder()
                .name(updateUserRequest.getName())
                .email(updateUserRequest.getEmail())
                .username(updateUserRequest.getUsername())
                .birthDate(updateUserRequest.getBirthDate()).build());
    }
}
