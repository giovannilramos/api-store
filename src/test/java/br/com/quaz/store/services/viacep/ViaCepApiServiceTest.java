package br.com.quaz.store.services.viacep;

import br.com.quaz.store.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ViaCepApiServiceTest {
    @Autowired
    private ViaCepApiService viaCepApiService;

    @Test
    void shouldReturnDataFromCep() {
        final var cep = "13052570";
        final var viaCepJson = viaCepApiService.getAddressByCep(cep);
        assertEquals("Campinas", viaCepJson.getCity());
        assertEquals("SP", viaCepJson.getState());
        assertEquals("Rua Gelsumino Lizardi", viaCepJson.getStreet());
        assertEquals("Jardim San Diego", viaCepJson.getDistrict());
    }

    @Test
    void shouldThrowBadRequestException() {
        final var cep = "213121";
        assertThrows(BadRequestException.class, () -> viaCepApiService.getAddressByCep(cep), "Incorrectly cep");
    }
}
