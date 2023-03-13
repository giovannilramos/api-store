package br.com.quaz.store.services.address;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteAddressService {
    private final AddressRepository addressRepository;

    public void deleteAddress(final UUID uuid) {
        final var product  = addressRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Address not found"));
        addressRepository.delete(product);
    }
}
