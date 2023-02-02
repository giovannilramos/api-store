package br.com.quaz.store.controllers.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductsListResponse extends RepresentationModel<ProductsListResponse> {
    private UUID uuid;
    private String name;
    private BigDecimal price;
    private Boolean isPromotion;
    private Integer discount;
    private String image;
}
