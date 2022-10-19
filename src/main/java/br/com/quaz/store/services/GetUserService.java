package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;

@Service
@RequiredArgsConstructor
public class GetUserService {
    private final UserRepository userRepository;

    public UserResponse findLoggedUser(final String jwtToken) {
        final var sub = decoderTokenJwt(jwtToken);

        var userOptional = userRepository.findByEmail(sub);
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(sub);
            if (userOptional.isEmpty()) {
                throw new NotFoundException("User not found");
            }
        }
        final var user = userOptional.get();
        return UserResponse.builder()
                .uuid(user.getUuid())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .username(user.getUsername()).build();

    }
}
