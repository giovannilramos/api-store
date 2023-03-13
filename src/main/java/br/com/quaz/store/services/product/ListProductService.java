package br.com.quaz.store.services.product;

import br.com.quaz.store.controllers.ProductController;
import br.com.quaz.store.controllers.response.ProductsListResponse;
import br.com.quaz.store.entities.Product;
import br.com.quaz.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static br.com.quaz.store.services.converters.ProductConverter.convertProductDTOToListResponse;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductEntityToDTO;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
@RequiredArgsConstructor
public class ListProductService {
    private final ProductRepository productRepository;

    public List<ProductsListResponse> listProducts(final Pageable pageable, final String categoryUuid,
                                                   final String name, final Boolean isPromotion) {
        final var exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("name", contains().ignoreCase())
                .withMatcher("isPromotion", exact());

        final var product = Product.builder().name(name).isPromotion(isPromotion).build();
        final var listProducts = productRepository.findAll(Example.of(product, exampleMatcher), pageable);

        return listProducts
                .filter(f -> !Objects.nonNull(categoryUuid) || categoryUuid.isEmpty() || f.getCategory().getUuid().toString().equals(categoryUuid))
                .map(productPage -> {
                    final var response = convertProductDTOToListResponse(convertProductEntityToDTO(productPage));
                    response.add(linkTo(methodOn(ProductController.class).findProductById(response.getUuid())).withSelfRel());
                    return response;
                }).toList();
    }
}
