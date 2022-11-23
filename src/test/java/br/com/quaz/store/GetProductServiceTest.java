package br.com.quaz.store;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.services.GetProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.productMock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class GetProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductService getProductService;

    @Test
    void shouldShowProductDetails() {
        final var uuid = UUID.randomUUID();
        final var productOptional = Optional.of(productMock("Teclado", "HyperX", BigDecimal.TEN, false, 0, "Teclado"));
        when(productRepository.findById(uuid)).thenReturn(productOptional);

        final var product = getProductService.findProductById(uuid);

        Assertions.assertEquals("Teclado", product.getName());
        Assertions.assertEquals("Quality Product", product.getDescription());
        Assertions.assertEquals(BigDecimal.TEN, product.getPrice());
        Assertions.assertEquals(false, product.getIsPromotion());
        Assertions.assertEquals(0, product.getDiscount());
        Assertions.assertEquals("https://google.com", product.getImage());
        Assertions.assertEquals("HyperX", product.getBrand());
        Assertions.assertEquals("Teclado", product.getCategory());
    }

    @Test
    void shouldThrowProductNotFound() {
        final var uuid = UUID.randomUUID();
        final Optional<Product> productOptional = Optional.empty();
        when(productRepository.findById(uuid)).thenReturn(productOptional);

        assertThrows(NotFoundException.class, () -> getProductService.findProductById(uuid), "Product Not Found");
    }
}
