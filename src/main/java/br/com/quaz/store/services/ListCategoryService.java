package br.com.quaz.store.services;

import br.com.quaz.store.repositories.CategoryRepository;
import br.com.quaz.store.response.CategoryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListCategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryListResponse> listCategory() {
        final var categoryList = categoryRepository.findAll();
        return categoryList.stream().map(category -> CategoryListResponse.builder()
                .uuid(category.getUuid())
                .name(category.getName()).build()).collect(Collectors.toList());
    }
}
