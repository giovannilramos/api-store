package br.com.quaz.store.services.address;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.addressMock;
import static br.com.quaz.store.mockHelper.MockHelper.token;
import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListAddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListAddressService listAddressService;

    @Test
    void shouldListAllAddressRegistered() {
        final var uuid = UUID.randomUUID();
        final var user = userMock(uuid, "Giovanni", "giovannilramos55@gmail.com", "giovanni.ramos");
        final var userOptional = Optional.of(user);
        final var address1 = addressMock(UUID.randomUUID(), user);
        final var address2 = addressMock(UUID.randomUUID(), user);

        final var addressList = Arrays.asList(address1, address2);

        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(userOptional);
        when(addressRepository.findAllByUser(user)).thenReturn(addressList);

        final var addressResponse = listAddressService.listAddresses(token);

        assertEquals(2, addressResponse.size());
        assertNotNull(addressResponse);
    }

    @Test
    void shouldThrowsUserNotFoundException() {
        assertThrows(NotFoundException.class, () -> listAddressService.listAddresses(token), "User not found");
    }

    @Test
    void shouldReturnEmptyList() {
        final var uuid = UUID.randomUUID();
        final var user = userMock(uuid, "Giovanni", "giovannilramos55@gmail.com", "giovanni.ramos");
        final var userOptional = Optional.of(user);

        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(userOptional);
        when(addressRepository.findAllByUser(user)).thenReturn(new ArrayList<>());

        final var addressResponse = listAddressService.listAddresses(token);

        assertEquals(0, addressResponse.size());
    }
}
