package br.com.quaz.store.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private LocalDate birthDate;
    private String username;
    private String email;
}
