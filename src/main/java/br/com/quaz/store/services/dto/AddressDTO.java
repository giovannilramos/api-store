package br.com.quaz.store.services.dto;

import br.com.quaz.store.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class AddressDTO {
    private UUID uuid;
    private String cep;
    private String number;
    private String street;
    private String district;
    private String country;
    private String city;
    private String state;
    private String complement;
    private User user;
}
