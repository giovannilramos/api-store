package br.com.quaz.store.services.category;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCategoryService {
    private final CategoryRepository categoryRepository;

    public void deleteCategory(final UUID uuid) {
        final var category  = categoryRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Category not found"));
        categoryRepository.delete(category);
    }
}
