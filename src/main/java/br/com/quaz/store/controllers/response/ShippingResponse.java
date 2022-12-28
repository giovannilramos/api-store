package br.com.quaz.store.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingResponse {
    private String estimatedDate;
    private String name;
    private BigInteger days;
    private BigDecimal value;
}
