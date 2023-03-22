package br.com.quaz.store.utils;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Map;

@Builder
@Getter
public class ExceptionValidatorsHeadersResponse {
    private String message;
    private ZonedDateTime zonedDateTime;
    private Map<String, String> errors;
}
