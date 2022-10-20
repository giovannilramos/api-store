package br.com.quaz.store.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaypalItemsDTO {
    private String name;
    private String description;
    private String quantity;
    @JsonProperty("unit_amount")
    private UnitAmountDTO unitAmountDTO;
}
