package br.com.quaz.store.services.category;

import br.com.quaz.store.controllers.request.CategoryRequest;
import br.com.quaz.store.controllers.response.CategoryListResponse;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.services.converters.CategoryConverter.convertCategoryDTOToEntity;
import static br.com.quaz.store.services.converters.CategoryConverter.convertCategoryDTOToResponse;
import static br.com.quaz.store.services.converters.CategoryConverter.convertCategoryEntityToDTO;
import static br.com.quaz.store.services.converters.CategoryConverter.convertCategoryRequestToDTO;

@Service
@RequiredArgsConstructor
public class CreateCategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryListResponse createCategory(final CategoryRequest categoryRequest) {
        if (Boolean.TRUE.equals(categoryRepository.existsByNameIgnoreCase(categoryRequest.getName()))) {
            throw new AlreadyExistsException("Category already exists");
        }
        final var category = categoryRepository.save(convertCategoryDTOToEntity(convertCategoryRequestToDTO(categoryRequest)));

        return convertCategoryDTOToResponse(convertCategoryEntityToDTO(category));
    }
}
