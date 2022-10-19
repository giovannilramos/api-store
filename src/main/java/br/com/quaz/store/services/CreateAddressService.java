package br.com.quaz.store.services;

import br.com.quaz.store.entities.Address;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;

@Service
@RequiredArgsConstructor
public class CreateAddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public void createAddress(final String jwtToken, final AddressRequest addressRequest) {
        final var sub = decoderTokenJwt(jwtToken);
        final var user = userRepository.findByEmail(sub)
                .orElseThrow(() -> new NotFoundException("User not found"));

        addressRepository.save(Address.builder()
                .cep(addressRequest.getCep())
                .number(addressRequest.getNumber())
                .street(addressRequest.getStreet())
                .district(addressRequest.getDistrict())
                .country(addressRequest.getCountry())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .complement(addressRequest.getComplement())
                .user(user).build());
    }
}
