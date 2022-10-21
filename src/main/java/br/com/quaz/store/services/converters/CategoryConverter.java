package br.com.quaz.store.services.converters;

import br.com.quaz.store.entities.Category;
import br.com.quaz.store.controllers.request.CategoryRequest;
import br.com.quaz.store.controllers.response.CategoryListResponse;
import br.com.quaz.store.services.dto.CategoryDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryConverter {
    public static CategoryDTO convertCategoryRequestToDTO(final CategoryRequest categoryRequest) {
        return CategoryDTO.builder().name(categoryRequest.getName()).build();
    }

    public static Category convertCategoryDTOToEntity(final CategoryDTO categoryDTO) {
        return Category.builder().name(categoryDTO.getName()).build();
    }

    public static CategoryDTO convertCategoryEntityToDTO(final Category category) {
        return CategoryDTO.builder().uuid(category.getUuid()).name(category.getName()).build();
    }

    public static CategoryListResponse convertCategoryDTOToResponse(final CategoryDTO categoryDTO) {
        return CategoryListResponse.builder().uuid(categoryDTO.getUuid()).name(categoryDTO.getName()).build();
    }
}
