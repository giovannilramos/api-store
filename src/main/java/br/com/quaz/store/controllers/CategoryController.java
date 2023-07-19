package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.request.CategoryRequest;
import br.com.quaz.store.controllers.response.CategoryListResponse;
import br.com.quaz.store.services.category.CreateCategoryService;
import br.com.quaz.store.services.category.DeleteCategoryService;
import br.com.quaz.store.services.category.GetCategoryService;
import br.com.quaz.store.services.category.ListCategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static br.com.quaz.store.helper.UriHelper.getUri;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final ListCategoryService listCategoryService;
    private final CreateCategoryService createCategoryService;
    private final DeleteCategoryService deleteCategoryService;
    private final GetCategoryService getCategoryService;

    @GetMapping
    public ResponseEntity<List<CategoryListResponse>> listCategory() {
        return ResponseEntity.ok(listCategoryService.listCategory());
    }

    @PostMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<CategoryListResponse> createCategory(@RequestBody @Valid final CategoryRequest categoryRequest, final UriComponentsBuilder uriComponentsBuilder) {
        final var categoryResponse = createCategoryService.createCategory(categoryRequest);
        final var uri = getUri(uriComponentsBuilder, "/category/{id}", categoryResponse.getUuid());
        return ResponseEntity.created(uri).body(categoryResponse);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") final UUID id) {
        deleteCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryListResponse> getCategoryById(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(getCategoryService.getCategoryById(id));
    }
}
