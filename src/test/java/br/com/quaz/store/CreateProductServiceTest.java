package br.com.quaz.store;

import br.com.quaz.store.controllers.request.ProductRequest;
import br.com.quaz.store.entities.Category;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.services.product.CreateProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceTest {
    private final Category category = Category.builder()
            .name("Teclado")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    private final ProductRequest productRequest = ProductRequest.builder()
            .name("Teclado Mercury")
            .brand("Razer")
            .categoryUuid(UUID.randomUUID())
            .description("Teclado bonito branco")
            .discount(0)
            .image("https://")
            .isPromotion(false)
            .price(BigDecimal.TEN)
            .build();
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CreateProductService createProductService;

    @Test
    void shouldCreateNewProduct() {
        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));
        when(productRepository.existsByNameIgnoreCaseAndBrand(any(), any())).thenReturn(Boolean.FALSE);

        createProductService.createProduct(productRequest);

        verify(productRepository, times(1)).save(any());
        assertDoesNotThrow(() -> createProductService.createProduct(productRequest));
    }

    @Test
    void shouldThrowProductAlreadyExistsExceptionWhenNameAndBrandAreEqualsToAnotherProduct() {
        when(productRepository.existsByNameIgnoreCaseAndBrand(any(), any())).thenReturn(Boolean.TRUE);

        assertThrows(AlreadyExistsException.class, () -> createProductService.createProduct(productRequest), "Product already exists");
    }

    @Test
    void shouldThrowCategoryNotFoundException() {
        when(productRepository.existsByNameIgnoreCaseAndBrand(any(), any())).thenReturn(Boolean.FALSE);

        assertThrows(NotFoundException.class, () -> createProductService.createProduct(productRequest), "Category not found");
    }
}
