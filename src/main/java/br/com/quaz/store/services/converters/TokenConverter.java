package br.com.quaz.store.services.converters;

import br.com.quaz.store.controllers.response.TokenJwtResponse;
import br.com.quaz.store.services.dto.TokenDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenConverter {
    public static TokenDTO convertToTokenDTO(final String token) {
        return TokenDTO.builder().token(token).build();
    }

    public static TokenJwtResponse convertTokenDTOToTokenResponse(final TokenDTO tokenDTO) {
        return TokenJwtResponse.builder().token(tokenDTO.getToken()).build();
    }
}
