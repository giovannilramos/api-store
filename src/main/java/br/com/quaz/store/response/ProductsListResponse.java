package br.com.quaz.store.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductsListResponse {
    private UUID uuid;
    private String name;
    private BigDecimal price;
    private Boolean isPromotion;
    private Integer discount;
    private String image;
}
