package br.com.quaz.store.services;

import br.com.quaz.store.entities.Category;
import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.request.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(final CategoryRequest categoryRequest) {
        final var category = new Category();

        category.setName(categoryRequest.getName());

        categoryRepository.save(category);
    }
}
