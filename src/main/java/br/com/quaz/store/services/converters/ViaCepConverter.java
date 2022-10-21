package br.com.quaz.store.services.converters;

import br.com.quaz.store.controllers.response.ViaCepResponse;
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

    public static ViaCepDTO convertViaCepRequestToDTO(final String city, final String state,
                                                      final String complement, final String street,
                                                      final String district) {
        return ViaCepDTO.builder()
                .city(city)
                .state(state)
                .complement(complement)
                .street(street)
                .district(district)
                .build();
    }
}
