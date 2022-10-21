package br.com.quaz.store.services;

import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.controllers.response.CategoryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                ).collect(Collectors.toList());
    }
}
