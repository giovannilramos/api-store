package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.response.ProductResponse;
import br.com.quaz.store.enums.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProductService {
    private final ProductRepository productRepository;

    public ProductResponse findProductById(final UUID uuid) {
        final var product  = productRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Product not found", StatusCode.NOT_FOUND.getStatusCode()));
        final var productResponse = new ProductResponse();

        productResponse.setUuid(product.getUuid());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setIsPromotion(product.getIsPromotion());
        productResponse.setDiscount(product.getDiscount());
        productResponse.setImage(product.getImage());
        productResponse.setCategory(product.getCategory());
        productResponse.setBrand(product.getBrand());

        return productResponse;
    }
}
