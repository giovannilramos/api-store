package br.com.quaz.store.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class UserRequest {
    private String name;
    private LocalDate birthDate;
    private String username;
    private String email;
    private String password;
    private Set<UUID> rolesUuid;
    private String cep;
    private String number;
    private String street;
    private String district;
    private String country;
    private String city;
    private String state;
    private String complement;
}
