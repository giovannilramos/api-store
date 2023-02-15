package br.com.quaz.store.handler;

import br.com.quaz.store.exceptions.BaseException;
import br.com.quaz.store.utils.ExceptionResponse;
import br.com.quaz.store.utils.ExceptionValidatorsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ExceptionResponse> resourceNotFoundExceptionHandler(final BaseException ex) {
        final var statusCode = HttpStatus.valueOf(ex.getCode());
        final var exception = ExceptionResponse.builder()
                .message(ex.getMessage())
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))).build();
        return new ResponseEntity<>(exception, statusCode);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionValidatorsResponse> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        final var errors = new HashMap<String, List<String>>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            final var fieldName = ((FieldError) error).getField();
            final var errorMessage = error.getDefaultMessage();

            errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        });

        final var exceptionValidatorsResponse = ExceptionValidatorsResponse.builder()
                .message("Bad Request")
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(exceptionValidatorsResponse);
    }
}
