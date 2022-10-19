package br.com.quaz.store.helper;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.request.ProductRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductHelper {
    public static Product setProductEntity(final Product product, final ProductRequest productRequest, final CategoryRepository productRepository) {
        final var category = productRepository.findById(productRequest.getCategoryUuid())
                .orElseThrow(() ->
                        new NotFoundException("Category not found"));
        return product.toBuilder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .isPromotion(productRequest.getIsPromotion())
                .discount(productRequest.getDiscount())
                .image(productRequest.getImage())
                .brand(productRequest.getBrand())
                .category(category).build();
    }
}
