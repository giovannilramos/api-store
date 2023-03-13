package br.com.quaz.store;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.services.product.DeleteProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.productMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private DeleteProductService deleteProductService;

    @Test
    void shouldDeleteProduct() {
        final var product = productMock(UUID.randomUUID(), "Teclado",
                "Logitech", BigDecimal.TEN, false, 0, "Teclado");
        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        deleteProductService.deleteProduct(product.getUuid());

        verify(productRepository, times(1)).delete(product);
        assertDoesNotThrow(() -> deleteProductService.deleteProduct(product.getUuid()));
    }

    @Test
    void shouldThrowProductNotFoundException() {
        final var uuid = UUID.randomUUID();
        assertThrows(NotFoundException.class, () -> deleteProductService.deleteProduct(uuid), "Product not found");
    }
}
