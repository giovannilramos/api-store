package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.controllers.request.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.quaz.store.services.converters.AddressConverter.convertAddressDTOToEntity;
import static br.com.quaz.store.services.converters.AddressConverter.convertAddressEntityToDTO;
import static br.com.quaz.store.services.converters.AddressConverter.convertAddressRequestToDTO;

@Service
@RequiredArgsConstructor
public class UpdateAddressService {
    private final AddressRepository addressRepository;

    public void updateAddress(final UUID uuid, final AddressRequest addressRequest) {
        final var address = addressRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Address not found"));
        final var addressDTO = convertAddressEntityToDTO(address);
        addressRepository.save(convertAddressDTOToEntity(convertAddressRequestToDTO(addressRequest, addressDTO)));
    }
}
