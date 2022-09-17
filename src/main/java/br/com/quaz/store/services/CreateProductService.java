package br.com.quaz.store.services;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.enums.StatusCode;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.helper.ProductHelper.setProductEntity;

@Service
@RequiredArgsConstructor
public class CreateProductService {
    private final ProductRepository productRepository;

    public void createProduct(final ProductRequest productRequest) {
        if (productRepository.existsByNameIgnoreCaseAndBrand(productRequest.getName(), productRequest.getBrand())) {
            throw new AlreadyExistsException("Product already exists", StatusCode.ALREADY_EXISTS.getStatusCode());
        }
        final var product = new Product();

        setProductEntity(product, productRequest);

        productRepository.save(product);
    }
}
