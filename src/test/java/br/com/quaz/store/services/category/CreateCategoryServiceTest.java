package br.com.quaz.store.services.category;

import br.com.quaz.store.controllers.request.CategoryRequest;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCategoryServiceTest {
    private final CategoryRequest categoryRequest = CategoryRequest.builder().name("Teclado").build();
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CreateCategoryService createCategoryService;

    @Test
    void shouldCreateNewCategory() {
        when(categoryRepository.existsByNameIgnoreCase(any())).thenReturn(Boolean.FALSE);

        createCategoryService.createCategory(this.categoryRequest);

        verify(categoryRepository, times(1)).save(any());
        assertDoesNotThrow(() -> createCategoryService.createCategory(this.categoryRequest));
    }

    @Test
    void shouldThrowCategoryAlreadyExistsExceptionWhenTryToCreateAnAlreadyExistentCategory() {
        when(categoryRepository.existsByNameIgnoreCase(any())).thenReturn(Boolean.TRUE);

        assertThrows(AlreadyExistsException.class, () -> createCategoryService.createCategory(this.categoryRequest), "Category already exists");
    }
}
