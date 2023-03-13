package br.com.quaz.store.services.product;

import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.controllers.request.ProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.quaz.store.services.converters.ProductConverter.convertProductDTOToEntity;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductEntityToDTO;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductRequestToDTO;

@Service
@AllArgsConstructor
public class UpdateProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void updateProduct(final UUID uuid, final ProductRequest productRequest) {
        final var product = productRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Product not found"));
        if (Boolean.TRUE.equals(productRepository.existsByNameIgnoreCaseAndBrand(productRequest.getName(), productRequest.getBrand()))) {
            throw new AlreadyExistsException("Product already exists");
        }
        final var category = categoryRepository.findById(productRequest.getCategoryUuid())
                .orElseThrow(() ->
                        new NotFoundException("Category not found"));
        final var productDTO = convertProductEntityToDTO(product);

        productRepository.save(convertProductDTOToEntity(convertProductRequestToDTO(productRequest, category, productDTO)));
    }
}
