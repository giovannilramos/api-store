package br.com.quaz.store.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
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
