package br.com.quaz.store.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class AddressRequest {
    private String cep;
    private String number;
    private String street;
    private String district;
    private String country;
    private String city;
    private String state;
    private String complement;
}
