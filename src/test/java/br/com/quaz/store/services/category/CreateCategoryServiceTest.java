package br.com.quaz.store.services.category;

import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.quaz.store.mockHelper.MockHelper.categoryMock;
import static br.com.quaz.store.mockHelper.MockHelper.categoryRequestMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CreateCategoryService createCategoryService;

    @Test
    void shouldCreateNewCategory() {
        final var categoryMock = categoryMock("Teclado");
        when(categoryRepository.existsByNameIgnoreCase(any())).thenReturn(Boolean.FALSE);
        when(categoryRepository.save(any())).thenReturn(categoryMock);

        final var category = createCategoryService.createCategory(categoryRequestMock());

        assertEquals(categoryMock.getUuid(), category.getUuid());
        assertEquals(categoryMock.getName(), category.getName());
    }

    @Test
    void shouldThrowCategoryAlreadyExistsExceptionWhenTryToCreateAnAlreadyExistentCategory() {
        final var categoryRequest = categoryRequestMock();
        when(categoryRepository.existsByNameIgnoreCase(any())).thenReturn(Boolean.TRUE);
        assertThrows(AlreadyExistsException.class, () -> createCategoryService.createCategory(categoryRequest), "Category already exists");
    }
}
