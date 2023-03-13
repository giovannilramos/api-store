package br.com.quaz.store;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.services.category.DeleteCategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.categoryMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteCategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private DeleteCategoryService deleteCategoryService;

    @Test
    void shouldDeleteCategory() {
        final var category = categoryMock("Teclado");
        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));

        deleteCategoryService.deleteCategory(category.getUuid());

        verify(categoryRepository, times(1)).delete(category);
        assertDoesNotThrow(() -> deleteCategoryService.deleteCategory(category.getUuid()));
    }

    @Test
    void shouldThrowCategoryNotFoundException() {
        final var uuid = UUID.randomUUID();
        assertThrows(NotFoundException.class, () -> deleteCategoryService.deleteCategory(uuid), "Category not found");
    }
}
