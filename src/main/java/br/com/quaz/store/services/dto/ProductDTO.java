package br.com.quaz.store.services.dto;

import br.com.quaz.store.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class ProductDTO {
    private UUID uuid;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Boolean isPromotion;
    private Integer discount;
    private String image;
    private Category category;
}
