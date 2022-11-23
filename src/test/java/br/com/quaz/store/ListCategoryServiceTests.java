package br.com.quaz.store;

import br.com.quaz.store.entities.Category;
import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.services.ListCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ListCategoryService listCategoryService;

    @Test
    void shouldListAllCategories() {
        when(categoryRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Category(UUID.randomUUID(), "Mouse", LocalDateTime.now(), LocalDateTime.now()),
                        new Category(UUID.randomUUID(), "Teclado", LocalDateTime.now(), LocalDateTime.now())
                )
        );
        final var categoryList = listCategoryService.listCategory();
        Assertions.assertEquals(2, categoryList.size());
        Assertions.assertNotNull(categoryList);
    }

    @Test
    void shouldReturnEmptyList() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        final var categoryList = listCategoryService.listCategory();
        Assertions.assertEquals(0, categoryList.size());
        Assertions.assertEquals(new ArrayList<>(), categoryList);
    }

}
