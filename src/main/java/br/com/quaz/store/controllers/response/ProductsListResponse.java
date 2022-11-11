package br.com.quaz.store.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsListResponse extends RepresentationModel<ProductsListResponse> {
    private UUID uuid;
    private String name;
    private BigDecimal price;
    private Boolean isPromotion;
    private Integer discount;
    private String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductsListResponse)) return false;
        if (!super.equals(o)) return false;
        ProductsListResponse that = (ProductsListResponse) o;
        return uuid.equals(that.uuid) && name.equals(that.name) && price.equals(that.price) && isPromotion.equals(that.isPromotion) && discount.equals(that.discount) && image.equals(that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), uuid, name, price, isPromotion, discount, image);
    }
}
