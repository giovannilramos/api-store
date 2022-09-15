package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.request.ProductRequest;
import br.com.quaz.store.enums.StatusCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.quaz.store.helper.ProductHelper.setProductEntity;

@Service
@AllArgsConstructor
public class UpdateProductService {
    private final ProductRepository productRepository;

    public void updateProduct(final UUID uuid, final ProductRequest productRequest) {
        final var product = productRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Product not found", StatusCode.NOT_FOUND.getStatusCode()));

        setProductEntity(product, productRequest);

        productRepository.save(product);
    }
}
