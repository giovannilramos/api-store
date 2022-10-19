package br.com.quaz.store.services;

import br.com.quaz.store.response.ViaCepResponse;
import br.com.quaz.store.integrations.ViaCepIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepApiService {
    private final ViaCepIntegration viaCepIntegration;

    public ViaCepResponse getAddressByCep(final String cep) {
        final var jsonAddress = viaCepIntegration.getCep(cep);

        return ViaCepResponse.builder()
                .city(jsonAddress.get("localidade").asText())
                .state(jsonAddress.get("uf").asText())
                .complement(jsonAddress.get("complemento").asText())
                .street(jsonAddress.get("logradouro").asText())
                .district(jsonAddress.get("bairro").asText())
                .build();
    }
}
