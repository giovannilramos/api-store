package br.com.quaz.store.handler;

import br.com.quaz.store.exceptions.BaseException;
import br.com.quaz.store.utils.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ExceptionResponse> resourceNotFoundExceptionHandler(final BaseException ex) {
        final var statusCode = HttpStatus.valueOf(ex.getCode());
        final var exception = ExceptionResponse.builder()
                .message(ex.getMessage())
                .httpStatus(statusCode)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))).build();
        return new ResponseEntity<>(exception, statusCode);
    }
}
