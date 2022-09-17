package br.com.quaz.store.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private String name;
    private LocalDate birthDate;
    private String username;
    private String email;
}
