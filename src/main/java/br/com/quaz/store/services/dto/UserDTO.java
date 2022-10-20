package br.com.quaz.store.services.dto;

import br.com.quaz.store.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class UserDTO {
    private UUID uuid;
    private String name;
    private String email;
    private String username;
    private String password;
    private LocalDate birthDate;
    private Set<Roles> roles;
}
