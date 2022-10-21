package br.com.quaz.store.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private UUID uuid;
    private String street;
    private String district;
    private String city;
    private String number;
    private String cep;
    private String country;
    private String state;
    private String complement;
}
