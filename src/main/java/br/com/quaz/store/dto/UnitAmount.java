package br.com.quaz.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnitAmount {
    @JsonProperty("currency_code")
    private String currencyCode;
    private String value;
}
