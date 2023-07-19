package br.com.quaz.store.services.address;

import br.com.quaz.store.controllers.request.AddressRequest;
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

import static br.com.quaz.store.mockHelper.MockHelper.token;
import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAddressServiceTest {
    private final AddressRequest addressRequest = AddressRequest.builder()
            .cep("12345670")
            .city("Campinas")
            .country("BRL")
            .district("Bairro a")
            .number("123")
            .street("Rua B")
            .build();
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private CreateAddressService createAddressService;

    @Test
    void shouldCreateAddress() {
        final var user = userMock(UUID.randomUUID(), "Giovanni Ramos", "giovannilramos55@gmail.com", "giovanni.ramos");

        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(Optional.of(user));

        createAddressService.createAddress(token, this.addressRequest);

        verify(addressRepository, times(1)).save(any());
        assertDoesNotThrow(() -> createAddressService.createAddress(token, this.addressRequest));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserIsNotLoggedIn() {
        assertThrows(NotFoundException.class, () -> createAddressService.createAddress(token, this.addressRequest), "User not found");
    }
}
