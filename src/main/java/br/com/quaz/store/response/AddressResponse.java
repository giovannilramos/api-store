package br.com.quaz.store.response;

import lombok.Data;

@Data
public class AddressResponse {
    private String street;
    private String district;
    private String city;
    private String state;
    private String complement;
}
