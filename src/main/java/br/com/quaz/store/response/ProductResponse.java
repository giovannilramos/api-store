package br.com.quaz.store.response;

import br.com.quaz.store.entities.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductResponse {
    private UUID uuid;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isPromotion;
    private Integer discount;
    private String image;
    private Category category;
}
