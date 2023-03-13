package br.com.quaz.store.services.address;

import br.com.quaz.store.controllers.response.AddressResponse;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;
import static br.com.quaz.store.services.converters.AddressConverter.convertAddressDTOToResponse;
import static br.com.quaz.store.services.converters.AddressConverter.convertAddressEntityToDTO;

@Service
@RequiredArgsConstructor
public class GetAddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    public AddressResponse getAddressById(final UUID uuid, final String jwtToken) {
        final var email = decoderTokenJwt(jwtToken);

        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        final var address = addressRepository.findByUserAndUuid(user, uuid)
                .orElseThrow(() -> new NotFoundException("Address not found"));
        return convertAddressDTOToResponse(convertAddressEntityToDTO(address));
    }
}
