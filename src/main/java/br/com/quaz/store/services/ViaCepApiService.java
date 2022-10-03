package br.com.quaz.store.services;

import br.com.quaz.store.response.AddressResponse;
import br.com.quaz.store.integrations.ViaCepIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepApiService {
    private final ViaCepIntegration viaCepIntegration;

    public AddressResponse getAddressByCep(final String cep) {
        final var jsonAddress = viaCepIntegration.getCep(cep);

        final var addressResponse = new AddressResponse();

        addressResponse.setCity(jsonAddress.get("localidade").asText());
        addressResponse.setState(jsonAddress.get("uf").asText());
        addressResponse.setComplement(jsonAddress.get("complemento").asText());
        addressResponse.setStreet(jsonAddress.get("logradouro").asText());
        addressResponse.setDistrict(jsonAddress.get("bairro").asText());

        return addressResponse;
    }
}
