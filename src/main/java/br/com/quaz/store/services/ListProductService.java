package br.com.quaz.store.services;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.response.ProductsListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;


@Service
@RequiredArgsConstructor
public class ListProductService {
    private final ProductRepository productRepository;

    public List<ProductsListResponse> listProducts(final Pageable pageable, final String categoryUuid,
                                                   final String name, final Boolean isPromotion) {
        final var exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("name", contains().ignoreCase())
                .withMatcher("isPromotion", exact());

        final var product = new Product(name, isPromotion);
        final var listProducts = productRepository.findAll(Example.of(product, exampleMatcher), pageable);

        return listProducts.stream()
                .filter(f -> !Objects.nonNull(categoryUuid) || categoryUuid.isEmpty() || f.getCategory().getUuid().toString().equals(categoryUuid))
                .map(productPage -> {
                    final var productsListResponse = new ProductsListResponse();
                    productsListResponse.setUuid(productPage.getUuid());
                    productsListResponse.setName(productPage.getName());
                    productsListResponse.setPrice(productPage.getPrice());
                    productsListResponse.setIsPromotion(productPage.getIsPromotion());
                    productsListResponse.setDiscount(productPage.getDiscount());
                    productsListResponse.setImage(productPage.getImage());
                    return productsListResponse;
                }).collect(Collectors.toList());
    }
}
