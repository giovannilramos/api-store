package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.request.ProductRequest;
import br.com.quaz.store.controllers.response.ProductResponse;
import br.com.quaz.store.controllers.response.ProductsListResponse;
import br.com.quaz.store.services.product.CreateProductService;
import br.com.quaz.store.services.product.DeleteProductService;
import br.com.quaz.store.services.product.GetProductService;
import br.com.quaz.store.services.product.ListProductService;
import br.com.quaz.store.services.product.UpdateProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static br.com.quaz.store.helper.UriHelper.getUri;

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
        return ResponseEntity.ok(listProductService.listProducts(pageable, category, name, isPromotion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(getProductService.findProductById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable(name = "id") final UUID id, @Valid @RequestBody final ProductRequest productRequest) {
        final var productResponse = updateProductService.updateProduct(id, productRequest);
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid  final ProductRequest productRequest, final UriComponentsBuilder uriComponentsBuilder) {
        final var productResponse = createProductService.createProduct(productRequest);
        final var uri = getUri(uriComponentsBuilder, "/products/{id}", productResponse.getUuid());
        return ResponseEntity.created(uri).body(productResponse);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") final UUID id) {
        deleteProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
