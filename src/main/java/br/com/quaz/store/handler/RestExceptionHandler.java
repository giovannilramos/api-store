package br.com.quaz.store.handler;

import br.com.quaz.store.exceptions.Exceptions;
import br.com.quaz.store.utils.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {Exceptions.class})
    public ResponseEntity<Object> resourceNotFoundExceptionHandler(final Exceptions ex) {
        final var statusCode = HttpStatus.valueOf(ex.getCode());
        final var exception = new ApiException(
                ex.getMessage(),
                statusCode,
                ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));

        return new ResponseEntity<>(exception, statusCode);
    }
}
