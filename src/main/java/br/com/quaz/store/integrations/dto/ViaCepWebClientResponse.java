package br.com.quaz.store.integrations.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViaCepWebClientResponse {
    @JsonAlias({"logradouro"})
    private String street;
    @JsonAlias({"complemento"})
    private String complement;
    @JsonAlias({"bairro"})
    private String district;
    @JsonAlias({"localidade"})
    private String city;
    @JsonAlias({"uf"})
    private String state;
}
