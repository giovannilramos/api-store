package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProductService {
    private final ProductRepository productRepository;

    public ProductResponse findProductById(final UUID uuid) {
        final var product  = productRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return ProductResponse.builder()
                .uuid(product.getUuid())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .isPromotion(product.getIsPromotion())
                .discount(product.getDiscount())
                .image(product.getImage())
                .category(product.getCategory().getName())
                .brand(product.getBrand()).build();
    }
}
