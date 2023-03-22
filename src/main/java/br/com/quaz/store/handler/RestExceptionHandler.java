package br.com.quaz.store.handler;

import br.com.quaz.store.exceptions.BaseException;
import br.com.quaz.store.utils.ExceptionResponse;
import br.com.quaz.store.utils.ExceptionValidatorsHeadersResponse;
import br.com.quaz.store.utils.ExceptionValidatorsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    public static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ExceptionResponse> resourceNotFoundExceptionHandler(final BaseException ex) {
        final var statusCode = HttpStatus.valueOf(ex.getCode());
        final var exception = ExceptionResponse.builder()
                .message(ex.getMessage())
                .zonedDateTime(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO))).build();
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
                .zonedDateTime(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)))
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(exceptionValidatorsResponse);
    }

    @ExceptionHandler(value = {MissingRequestHeaderException.class})
    public ResponseEntity<ExceptionValidatorsHeadersResponse> handleValidationHeadersExceptions(final MissingRequestHeaderException ex) {
        final var errors = new HashMap<String, String>();

        errors.put(ex.getHeaderName(), ex.getMessage());

        final var exceptionValidatorsHeadersResponse = ExceptionValidatorsHeadersResponse.builder()
                .message("Bad Request")
                .zonedDateTime(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)))
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(exceptionValidatorsHeadersResponse);
    }
}
