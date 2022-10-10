package br.com.quaz.store.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiException {
    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime zonedDateTime;
}
