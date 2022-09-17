package br.com.quaz.store.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID uuid;
    private String name;
    private LocalDate birthDate;
    private String username;
    private String email;
}
