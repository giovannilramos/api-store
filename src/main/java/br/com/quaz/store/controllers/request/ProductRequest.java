package br.com.quaz.store.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotEmpty(message = "brand cannot be empty")
    private String brand;
    @NotEmpty(message = "description cannot be empty")
    private String description;
    @NotNull(message = "price cannot be null")
    @DecimalMin(value = "0.01", message = "price must be greater than 0")
    private BigDecimal price;
    @NotNull(message = "isPromotion cannot be null")
    private Boolean isPromotion;
    @NotNull(message = "discount cannot be null")
    @PositiveOrZero(message = "discount must be greater or equals to 0")
    private Integer discount;
    @NotEmpty(message = "image cannot be empty")
    private String image;
    @NotNull(message = "categoryUuid cannot be null")
    private UUID categoryUuid;
}
