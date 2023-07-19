package br.com.quaz.store.services.viacep;

import br.com.quaz.store.controllers.response.ViaCepResponse;
import br.com.quaz.store.exceptions.BadRequestException;
import br.com.quaz.store.integrations.ViaCepIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.services.converters.ViaCepConverter.convertViaCepDTOToResponse;
import static br.com.quaz.store.services.converters.ViaCepConverter.convertViaCepRequestToDTO;

@Service
@RequiredArgsConstructor
public class ViaCepApiService {
    private final ViaCepIntegration viaCepIntegration;

    public ViaCepResponse getAddressByCep(final String cep) {
        try {
            final var viaCepWebClientResponse = viaCepIntegration.getCep(cep);
            return convertViaCepDTOToResponse(convertViaCepRequestToDTO(viaCepWebClientResponse));
        } catch (final Exception e) {
            throw new BadRequestException("Incorrectly cep");
        }
    }
}
