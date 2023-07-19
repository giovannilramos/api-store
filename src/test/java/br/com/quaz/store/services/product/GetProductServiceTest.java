package br.com.quaz.store.services.product;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.productMock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductService getProductService;

    @Test
    void shouldShowProductDetails() {
        final var uuid = UUID.randomUUID();
        final var productOptional = Optional.of(productMock(uuid, "Teclado", "HyperX", BigDecimal.TEN, false, 0, "Teclado"));
        when(productRepository.findById(uuid)).thenReturn(productOptional);

        final var product = getProductService.findProductById(uuid);

        Assertions.assertEquals("Teclado", product.getName());
        Assertions.assertEquals(uuid, product.getUuid());
        Assertions.assertEquals("Quality product", product.getDescription());
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

        assertThrows(NotFoundException.class, () -> getProductService.findProductById(uuid), "Product not found");
    }
}
