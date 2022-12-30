package br.com.quaz.store.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String message;
    private ZonedDateTime zonedDateTime;
}
