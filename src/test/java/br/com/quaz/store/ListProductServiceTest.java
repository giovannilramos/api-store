package br.com.quaz.store;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.services.ListProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.productMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@ExtendWith(MockitoExtension.class)
class ListProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ListProductService listProductService;
//TODO: Testes não funcionando
    @Test
    void shouldReturnAllProducts() {
        final var product1 = productMock(UUID.randomUUID(), "Teclado", "HyperX",
                BigDecimal.TEN, false, 0, "Teclado");
        final var product2 = productMock(UUID.randomUUID(), "Mouse", "Logitech",
                BigDecimal.TEN, false, 0, "Mouse");
        final var productList = Arrays.asList(product1, product2);
        final var exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("name", contains().ignoreCase())
                .withMatcher("isPromotion", exact());
        final Pageable pageable = PageRequest.of(0, 10);
        final var product = Product.builder().name("").isPromotion(null).build();

        lenient().when(productRepository.findAll(Example.of(product, exampleMatcher), pageable)).thenReturn(new PageImpl<>(productList));

        final var productResponse = listProductService.listProducts(
                pageable, "", "", null
        );

        assertEquals(2, productResponse.size());
        assertNotNull(productResponse);
    }

    @Test
    void shouldReturn1ProductInFirstPage() {
        final var product1 = productMock(UUID.randomUUID(), "Mouse", "Logitech",
                BigDecimal.ONE, false, 0, "Mouse");
        final var product2 = productMock(UUID.randomUUID(), "Teclado", "HyperX",
                BigDecimal.TEN, false, 0, "Teclado");
        final var productList = Arrays.asList(product1, product2);
        final var exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("name", contains().ignoreCase())
                .withMatcher("isPromotion", exact());
        final Pageable pageable = PageRequest.of(0, 1);
        final var product = Product.builder().name(null).isPromotion(null).build();

        when(productRepository.findAll(Example.of(product, exampleMatcher), pageable)).thenReturn(new PageImpl<>(productList));


        final var productResponse = listProductService.listProducts(
                pageable, null, null, null
        );

        assertEquals(1, productResponse.size());
        assertEquals("Mouse", productResponse.stream().findFirst().orElseThrow().getName());
        assertEquals(BigDecimal.ONE, productResponse.stream().findFirst().orElseThrow().getPrice());
        assertNotNull(productResponse);
    }
}
