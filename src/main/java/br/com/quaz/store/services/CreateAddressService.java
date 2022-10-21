package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.controllers.request.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;
import static br.com.quaz.store.services.converters.AddressConverter.convertAddressDTOToEntity;
import static br.com.quaz.store.services.converters.AddressConverter.convertAddressRequestToDTO;

@Service
@RequiredArgsConstructor
public class CreateAddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public void createAddress(final String jwtToken, final AddressRequest addressRequest) {
        final var email = decoderTokenJwt(jwtToken);
        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        addressRepository.save(convertAddressDTOToEntity(convertAddressRequestToDTO(addressRequest, user)));
    }
}
