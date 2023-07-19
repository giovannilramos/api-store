package br.com.quaz.store.services.address;

import br.com.quaz.store.controllers.request.AddressRequest;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.addressMock;
import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private UpdateAddressService updateAddressService;

    @Test
    void shouldUpdateAddressData() {
        final var addressRequest = AddressRequest.builder().build();
        final var user = userMock(UUID.randomUUID(), "Giovanni", "test@test.com", "tester");
        final var address = addressMock(UUID.randomUUID(), user);

        when(addressRepository.findById(any())).thenReturn(Optional.of(address));

        assertDoesNotThrow(() -> updateAddressService.updateAddress(UUID.randomUUID(), addressRequest));
        verify(addressRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowNotFoundException() {
        final var addressRequest = AddressRequest.builder().build();
        final var uuid = UUID.randomUUID();
        when(addressRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> updateAddressService.updateAddress(uuid, addressRequest));
    }
}
