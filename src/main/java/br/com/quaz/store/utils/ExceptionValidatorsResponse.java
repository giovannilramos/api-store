package br.com.quaz.store.utils;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class ExceptionValidatorsResponse {
    private String message;
    private ZonedDateTime zonedDateTime;
    private Map<String, List<String>> errors;
}
