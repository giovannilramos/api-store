package br.com.quaz.store.request;

import br.com.quaz.store.entities.Roles;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserRequest {
    private String name;
    private LocalDate birthDate;
    private String username;
    private String password;
    private List<Roles> roles;
}
