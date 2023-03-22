package br.com.quaz.store.services.product;

import br.com.quaz.store.controllers.request.ProductRequest;
import br.com.quaz.store.controllers.response.ProductResponse;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.services.converters.ProductConverter.convertProductDTOToEntity;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductDTOToResponse;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductEntityToDTO;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductRequestToDTO;

@Service
@RequiredArgsConstructor
public class CreateProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse createProduct(final ProductRequest productRequest) {
        if (Boolean.TRUE.equals(productRepository.existsByNameIgnoreCaseAndBrand(productRequest.getName(), productRequest.getBrand()))) {
            throw new AlreadyExistsException("Product already exists");
        }
        final var category = categoryRepository.findById(productRequest.getCategoryUuid())
                .orElseThrow(() ->
                        new NotFoundException("Category not found"));

        final var product = productRepository.save(convertProductDTOToEntity(convertProductRequestToDTO(productRequest, category)));
        return convertProductDTOToResponse(convertProductEntityToDTO(product));
    }
}
