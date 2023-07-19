package br.com.quaz.store.services.address;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.addressMock;
import static br.com.quaz.store.mockHelper.MockHelper.addressRequestMock;
import static br.com.quaz.store.mockHelper.MockHelper.token;
import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAddressServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private CreateAddressService createAddressService;

    @Test
    void shouldCreateAddress() {
        final var user = userMock(UUID.randomUUID(), "Giovanni Ramos", "giovannilramos55@gmail.com", "giovanni.ramos");
        final var addressMock = addressMock(UUID.randomUUID(), user);

        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(Optional.of(user));
        when(addressRepository.save(any())).thenReturn(addressMock);

        final var address = createAddressService.createAddress(token, addressRequestMock());
        assertEquals(addressMock.getStreet(), address.getStreet());
        assertEquals(addressMock.getDistrict(), address.getDistrict());
        assertEquals(addressMock.getCity(), address.getCity());
        assertEquals(addressMock.getNumber(), address.getNumber());
        assertEquals(addressMock.getCep(), address.getCep());
        assertEquals(addressMock.getCountry(), address.getCountry());
        assertEquals(addressMock.getState(), address.getState());
        assertEquals(addressMock.getComplement(), address.getComplement());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserIsNotLoggedIn() {
        final var addressRequest = addressRequestMock();
        assertThrows(NotFoundException.class, () -> createAddressService.createAddress(token, addressRequest), "User not found");
    }
}
