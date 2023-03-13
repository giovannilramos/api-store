package br.com.quaz.store.services.product;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.controllers.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.quaz.store.services.converters.ProductConverter.convertProductDTOToResponse;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductEntityToDTO;

@Service
@RequiredArgsConstructor
public class GetProductService {
    private final ProductRepository productRepository;

    public ProductResponse findProductById(final UUID uuid) {
        final var product  = productRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return convertProductDTOToResponse(convertProductEntityToDTO(product));
    }
}
