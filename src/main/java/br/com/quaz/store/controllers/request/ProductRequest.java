package br.com.quaz.store.controllers.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class ProductRequest {
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Boolean isPromotion;
    private Integer discount;
    private String image;
    private UUID categoryUuid;
}
