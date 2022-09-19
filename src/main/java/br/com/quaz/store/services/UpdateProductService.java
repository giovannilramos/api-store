package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public void updateProduct(final UUID uuid, final ProductRequest productRequest) {
        final var product = productRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Product not found", StatusCode.NOT_FOUND.getStatusCode()));
        if (productRepository.existsByNameIgnoreCaseAndBrand(productRequest.getName(), productRequest.getBrand())) {
            throw new AlreadyExistsException("Product already exists", StatusCode.ALREADY_EXISTS.getStatusCode());
        }

        setProductEntity(product, productRequest, categoryRepository);

        productRepository.save(product);
    }
}
