package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.request.CategoryRequest;
import br.com.quaz.store.controllers.response.CategoryListResponse;
import br.com.quaz.store.services.CreateCategoryService;
import br.com.quaz.store.services.DeleteCategoryService;
import br.com.quaz.store.services.ListCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final ListCategoryService listCategoryService;
    private final CreateCategoryService createCategoryService;
    private final DeleteCategoryService deleteCategoryService;

    @GetMapping
    public ResponseEntity<List<CategoryListResponse>> listCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(listCategoryService.listCategory());
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody final CategoryRequest categoryRequest) {
        createCategoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") final UUID id) {
        deleteCategoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
