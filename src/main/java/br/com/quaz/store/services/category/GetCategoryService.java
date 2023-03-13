package br.com.quaz.store.services.category;

import br.com.quaz.store.controllers.response.CategoryListResponse;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.quaz.store.services.converters.CategoryConverter.convertCategoryDTOToResponse;
import static br.com.quaz.store.services.converters.CategoryConverter.convertCategoryEntityToDTO;

@Service
@RequiredArgsConstructor
public class GetCategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryListResponse getCategoryById(final UUID uuid) {
        final var category = categoryRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        return convertCategoryDTOToResponse(convertCategoryEntityToDTO(category));
    }
}
