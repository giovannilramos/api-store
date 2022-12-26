package br.com.quaz.store;

import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.services.UpdateAddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateAddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private UpdateAddressService updateAddressService;

    @Test
    void shouldUpdateAddressData() {

    }
}
