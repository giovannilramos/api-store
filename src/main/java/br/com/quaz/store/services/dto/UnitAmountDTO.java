package br.com.quaz.store.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UnitAmountDTO {
    @JsonProperty("currency_code")
    private String currencyCode;
    private String value;
}
