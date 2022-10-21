package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.controllers.request.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.services.converters.CategoryConverter.convertCategoryDTOToEntity;
import static br.com.quaz.store.services.converters.CategoryConverter.convertCategoryRequestToDTO;

@Service
@RequiredArgsConstructor
public class CreateCategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(final CategoryRequest categoryRequest) {
        if (Boolean.TRUE.equals(categoryRepository.existsByNameIgnoreCase(categoryRequest.getName()))) {
            throw new AlreadyExistsException("Category already exists");
        }
        categoryRepository.save(convertCategoryDTOToEntity(convertCategoryRequestToDTO(categoryRequest)));
    }
}
