package br.com.quaz.store.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ViaCepResponse {
    private String street;
    private String district;
    private String city;
    private String state;
    private String complement;
}
