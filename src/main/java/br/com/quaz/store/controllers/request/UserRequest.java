package br.com.quaz.store.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotNull(message = "birthDate cannot be null")
    @Past(message = "birthDate must be less than the current year")
    private LocalDate birthDate;
    @NotEmpty(message = "username cannot be empty")
    private String username;
    @NotEmpty(message = "email cannot be empty")
    @Email(message = "email must be from type E-mail")
    private String email;
    @NotEmpty(message = "password cannot be empty")
    private String password;
    @NotEmpty(message = "rolesUuid cannot be empty")
    private Set<UUID> rolesUuid;
    @NotEmpty(message = "taxId cannot be empty")
    @Size(min = 11, max = 11, message = "taxId must have 11 numbers")
    private String taxId;
}
