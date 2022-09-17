package br.com.quaz.store.request;

import br.com.quaz.store.entities.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Boolean isPromotion;
    private Integer discount;
    private String image;
    private Category category;
}
