package br.com.quaz.store.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
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
}
