package br.com.quaz.store;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.services.address.DeleteAddressService;
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
class DeleteAddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private DeleteAddressService deleteAddressService;

    @Test
    void shouldDeleteAddress() {
        final var user = userMock(UUID.randomUUID(), "Giovanni Ramos",
                "giovannilramos55@gmail.com", "giovanni.ramos");
        final var address = addressMock(UUID.randomUUID(), user);
        when(addressRepository.findById(any())).thenReturn(Optional.of(address));

        deleteAddressService.deleteAddress(any());

        verify(addressRepository, times(1)).delete(address);
        assertDoesNotThrow(() -> deleteAddressService.deleteAddress(any()));
    }

    @Test
    void shouldThrowAddressNotFoundException() {
        final var uuid = UUID.randomUUID();
        assertThrows(NotFoundException.class, () -> deleteAddressService.deleteAddress(uuid), "Address not found");
    }
}
