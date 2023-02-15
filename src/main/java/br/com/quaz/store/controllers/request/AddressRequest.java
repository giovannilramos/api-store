package br.com.quaz.store.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class AddressRequest {
    @NotEmpty(message = "cep cannot be empty")
    @Size(min = 8, max = 8, message = "cep must have 8 numbers")
    private String cep;
    @NotEmpty(message = "number cannot be empty")
    private String number;
    @NotEmpty(message = "street cannot be empty")
    private String street;
    @NotEmpty(message = "district cannot be empty")
    private String district;
    @NotEmpty(message = "country cannot be empty")
    @Size(min = 3, max = 3, message = "country must have a 3-letter acronym")
    private String country;
    @NotEmpty(message = "city cannot be empty")
    private String city;
    @NotEmpty(message = "state cannot be empty")
    @Size(min = 2, max = 2, message = "state must have a 2-letter acronym")
    private String state;
    private String complement;
}
