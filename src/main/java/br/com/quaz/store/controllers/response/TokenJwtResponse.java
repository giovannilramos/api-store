package br.com.quaz.store.controllers.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenJwtResponse {
    private String token;
}
