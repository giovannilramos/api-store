package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteProductService {
    private final ProductRepository productRepository;

    public void deleteProduct(final UUID uuid) {
        final var product  = productRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
