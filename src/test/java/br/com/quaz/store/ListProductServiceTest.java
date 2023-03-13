package br.com.quaz.store;

import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.services.product.ListProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.productMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ListProductService listProductService;

    @Test
    void shouldReturnAllProducts() {
        final var product1 = productMock(UUID.randomUUID(), "O Teclado", "HyperX",
                BigDecimal.TEN, false, 0, "Teclado");
        final var product2 = productMock(UUID.randomUUID(), "O Mouse", "Logitech",
                BigDecimal.TEN, false, 0, "Mouse");
        final var productList = Arrays.asList(product1, product2);
        final var pageable = PageRequest.of(0, 10);

        when(productRepository.findAll(any(), any(Pageable.class))).thenReturn(new PageImpl<>(productList));

        final var productResponse = listProductService.listProducts(pageable, null, null, null);

        assertEquals(2, productResponse.size());
        assertNotNull(productResponse);
    }

    @Test
//    TODO: Arrumar, falso teste ->
    void shouldReturn1ProductInFirstPage() {
        final var product1 = productMock(UUID.randomUUID(), "Mouse", "Logitech",
                BigDecimal.ONE, false, 0, "Mouse");
        final var product2 = productMock(UUID.randomUUID(), "Teclado", "HyperX",
                BigDecimal.TEN, false, 0, "Teclado");
        final var productList = Arrays.asList(product1, product2);
        final var pageable = PageRequest.of(0, 2);

        when(productRepository.findAll(any(), eq(pageable))).thenReturn(new PageImpl<>(List.of(productList.stream().findFirst().orElseThrow()), pageable, 10));


        final var productResponse = listProductService.listProducts(pageable, null, null, null);

        assertEquals(1, productResponse.size());
        assertEquals("Mouse", productResponse.stream().findFirst().orElseThrow().getName());
        assertEquals(BigDecimal.ONE, productResponse.stream().findFirst().orElseThrow().getPrice());
        assertNotNull(productResponse);
    }
}
