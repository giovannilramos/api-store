package br.com.quaz.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaypalItems {
    private String name;
    private String description;
    private String quantity;
    @JsonProperty("unit_amount")
    private UnitAmount unitAmount;
}
