package br.com.quaz.store.services.category;

import br.com.quaz.store.controllers.response.CategoryListResponse;
import br.com.quaz.store.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.quaz.store.services.converters.CategoryConverter.convertCategoryDTOToResponse;
import static br.com.quaz.store.services.converters.CategoryConverter.convertCategoryEntityToDTO;

@Service
@RequiredArgsConstructor
public class ListCategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryListResponse> listCategory() {
        final var categoryList = categoryRepository.findAll();
        return categoryList.stream().map(category ->
                        convertCategoryDTOToResponse(convertCategoryEntityToDTO(category))
                ).toList();
    }
}
