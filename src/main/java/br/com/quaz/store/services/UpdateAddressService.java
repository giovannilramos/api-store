package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.request.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateAddressService {
    private final AddressRepository addressRepository;

    public void updateAddress(final UUID uuid, final AddressRequest addressRequest) {
        final var address = addressRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        addressRepository.save(address.toBuilder()
                .cep(addressRequest.getCep())
                .number(addressRequest.getNumber())
                .street(addressRequest.getStreet())
                .district(addressRequest.getDistrict())
                .country(addressRequest.getCountry())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .complement(addressRequest.getComplement()).build());
    }
}
