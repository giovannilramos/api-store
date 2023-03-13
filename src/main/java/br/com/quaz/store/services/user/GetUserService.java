package br.com.quaz.store.services.user;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.controllers.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;
import static br.com.quaz.store.services.converters.UserConverter.convertUserDTOToResponse;
import static br.com.quaz.store.services.converters.UserConverter.convertUserEntityToDTO;

@Service
@RequiredArgsConstructor
public class GetUserService {
    private final UserRepository userRepository;

    public UserResponse findLoggedUser(final String jwtToken) {
        final var email = decoderTokenJwt(jwtToken);

        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return convertUserDTOToResponse(convertUserEntityToDTO(user));

    }
}
