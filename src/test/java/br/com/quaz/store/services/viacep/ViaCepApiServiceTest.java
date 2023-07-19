package br.com.quaz.store.services.viacep;

import br.com.quaz.store.exceptions.BadRequestException;
import br.com.quaz.store.integrations.ViaCepIntegration;
import br.com.quaz.store.integrations.dto.ViaCepWebClientResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViaCepApiServiceTest {
    @Mock
    private ViaCepIntegration viaCepIntegration;
    @InjectMocks
    private ViaCepApiService viaCepApiService;

    @Test
    void shouldReturnDataFromCep() {
        final var cep = "13052570";
        final var viaCepWebClientResponse = ViaCepWebClientResponse.builder()
                .city("Campinas")
                .state("SP")
                .complement("")
                .street("Rua Gelsumino Lizardi")
                .district("Jardim San Diego")
                .build();
        when(viaCepIntegration.getCep(cep)).thenReturn(viaCepWebClientResponse);
        final var viaCepResponse = viaCepApiService.getAddressByCep(cep);
        assertEquals("Campinas", viaCepResponse.getCity());
        assertEquals("SP", viaCepResponse.getState());
        assertEquals("Rua Gelsumino Lizardi", viaCepResponse.getStreet());
        assertEquals("Jardim San Diego", viaCepResponse.getDistrict());
    }

    @Test
    void shouldThrowBadRequestException() {
        final var cep = "213121";
        when(viaCepIntegration.getCep(cep)).thenThrow();
        assertThrows(BadRequestException.class, () -> viaCepApiService.getAddressByCep(cep), "Incorrectly cep");
    }
}
