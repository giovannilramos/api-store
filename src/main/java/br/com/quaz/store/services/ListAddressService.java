package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.response.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;

@Service
@RequiredArgsConstructor
public class ListAddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public List<AddressResponse> listAddresses(final String jwtToken) {
        final var email = decoderTokenJwt(jwtToken);

        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        final var addressList = addressRepository.findAllByUser(user);
        return addressList.stream().map(address -> AddressResponse.builder()
                .uuid(address.getUuid())
                .street(address.getStreet())
                .district(address.getDistrict())
                .city(address.getCity())
                .number(address.getNumber())
                .cep(address.getCep())
                .country(address.getCountry())
                .complement(address.getComplement())
                .state(address.getState()).build()).collect(Collectors.toList());
    }
}
