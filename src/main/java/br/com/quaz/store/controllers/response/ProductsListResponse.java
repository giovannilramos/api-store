package br.com.quaz.store.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsListResponse {
    private UUID uuid;
    private String name;
    private BigDecimal price;
    private Boolean isPromotion;
    private Integer discount;
    private String image;
}
