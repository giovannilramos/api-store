package br.com.quaz.store.helper;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.request.ProductRequest;

public class ProductHelper {
    public static void setProductEntity(final Product product, final ProductRequest productRequest, final CategoryRepository productRepository) {
        final var category = productRepository.findById(productRequest.getCategoryUuid())
                .orElseThrow(() ->
                        new NotFoundException("Category not found"));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setIsPromotion(productRequest.getIsPromotion());
        product.setDiscount(productRequest.getDiscount());
        product.setImage(productRequest.getImage());
        product.setBrand(productRequest.getBrand());
        product.setCategory(category);
    }
}
