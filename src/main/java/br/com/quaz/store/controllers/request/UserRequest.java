package br.com.quaz.store.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String name;
    private LocalDate birthDate;
    private String username;
    private String email;
    private String password;
    private Set<UUID> rolesUuid;
    private String taxId;
}
