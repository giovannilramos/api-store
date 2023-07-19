package br.com.quaz.store.services.converters;

import br.com.quaz.store.controllers.response.ViaCepResponse;
import br.com.quaz.store.integrations.dto.ViaCepWebClientResponse;
import br.com.quaz.store.services.dto.ViaCepDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViaCepConverter {
    public static ViaCepResponse convertViaCepDTOToResponse(final ViaCepDTO viaCepDTO) {
        return ViaCepResponse.builder()
                .city(viaCepDTO.getCity())
                .state(viaCepDTO.getState())
                .complement(viaCepDTO.getComplement())
                .street(viaCepDTO.getStreet())
                .district(viaCepDTO.getDistrict())
                .build();
    }

    public static ViaCepDTO convertViaCepRequestToDTO(final ViaCepWebClientResponse viaCepWebClientResponse) {
        return ViaCepDTO.builder()
                .city(viaCepWebClientResponse.getCity())
                .state(viaCepWebClientResponse.getState())
                .complement(viaCepWebClientResponse.getComplement())
                .street(viaCepWebClientResponse.getStreet())
                .district(viaCepWebClientResponse.getDistrict())
                .build();
    }
}
