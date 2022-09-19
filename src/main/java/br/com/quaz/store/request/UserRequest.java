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
}
