package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.controllers.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.services.converters.ProductConverter.convertProductDTOToEntity;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductRequestToDTO;

@Service
@RequiredArgsConstructor
public class CreateProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void createProduct(final ProductRequest productRequest) {
        if (Boolean.TRUE.equals(productRepository.existsByNameIgnoreCaseAndBrand(productRequest.getName(), productRequest.getBrand()))) {
            throw new AlreadyExistsException("Product already exists");
        }
        final var category = categoryRepository.findById(productRequest.getCategoryUuid())
                .orElseThrow(() ->
                        new NotFoundException("Category not found"));

        productRepository.save(convertProductDTOToEntity(convertProductRequestToDTO(productRequest, category)));
    }
}
