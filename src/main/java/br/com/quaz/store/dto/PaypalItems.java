package br.com.quaz.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaypalItems {
    private String name;
    private String description;
    private String quantity;
    @JsonProperty("unit_amount")
    private UnitAmount unitAmount;
}
