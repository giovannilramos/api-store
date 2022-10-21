package br.com.quaz.store.services;

import br.com.quaz.store.integrations.ViaCepIntegration;
import br.com.quaz.store.controllers.response.ViaCepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.services.converters.ViaCepConverter.convertViaCepDTOToResponse;
import static br.com.quaz.store.services.converters.ViaCepConverter.convertViaCepRequestToDTO;

@Service
@RequiredArgsConstructor
public class ViaCepApiService {
    private final ViaCepIntegration viaCepIntegration;

    public ViaCepResponse getAddressByCep(final String cep) {
        final var jsonAddress = viaCepIntegration.getCep(cep);
        return convertViaCepDTOToResponse(convertViaCepRequestToDTO(jsonAddress.get("localidade").asText(),
                jsonAddress.get("uf").asText(), jsonAddress.get("complemento").asText(),
                jsonAddress.get("logradouro").asText(), jsonAddress.get("bairro").asText()));
    }
}
