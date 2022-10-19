package br.com.quaz.store.controllers;

import br.com.quaz.store.request.ProductRequest;
import br.com.quaz.store.response.ProductResponse;
import br.com.quaz.store.response.ProductsListResponse;
import br.com.quaz.store.services.CreateProductService;
import br.com.quaz.store.services.DeleteProductService;
import br.com.quaz.store.services.GetProductService;
import br.com.quaz.store.services.ListProductService;
import br.com.quaz.store.services.UpdateProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ListProductService listProductService;
    private final GetProductService getProductService;
    private final UpdateProductService updateProductService;
    private final CreateProductService createProductService;
    private final DeleteProductService deleteProductService;

    @GetMapping
    public ResponseEntity<List<ProductsListResponse>> listProducts(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable,
            @RequestParam(required = false, name = "category") final String category,
            @RequestParam(required = false, name = "name") final String name,
            @RequestParam(required = false, name = "isPromotion") final Boolean isPromotion) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(listProductService.listProducts(pageable, category, name, isPromotion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(getProductService.findProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable(name = "id") final UUID id, @RequestBody final ProductRequest productRequest) {
        updateProductService.updateProduct(id, productRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody final ProductRequest productRequest) {
        createProductService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") final UUID id) {
        deleteProductService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
