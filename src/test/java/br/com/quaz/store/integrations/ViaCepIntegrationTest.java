package br.com.quaz.store.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ViaCepIntegrationTest {
    @Autowired
    private ViaCepIntegration viaCepIntegration;
    @Test
    void shouldReturnViaCepCall() {
        final var viaCepWebClientResponse = viaCepIntegration.getCep("13052570");
        assertEquals("Campinas", viaCepWebClientResponse.getCity());
        assertEquals("SP", viaCepWebClientResponse.getState());
        assertEquals("Rua Gelsumino Lizardi", viaCepWebClientResponse.getStreet());
        assertEquals("Jardim San Diego", viaCepWebClientResponse.getDistrict());
        assertEquals("", viaCepWebClientResponse.getComplement());
    }
}