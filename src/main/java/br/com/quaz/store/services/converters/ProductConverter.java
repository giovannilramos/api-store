package br.com.quaz.store.services.converters;

import br.com.quaz.store.entities.Category;
import br.com.quaz.store.entities.Product;
import br.com.quaz.store.controllers.request.ProductRequest;
import br.com.quaz.store.controllers.response.ProductResponse;
import br.com.quaz.store.controllers.response.ProductsListResponse;
import br.com.quaz.store.services.dto.ProductDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConverter {
    public static Product convertProductDTOToEntity(final ProductDTO productDTO) {
        return Product.builder()
                .uuid(productDTO.getUuid())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .isPromotion(productDTO.getIsPromotion())
                .discount(productDTO.getDiscount())
                .image(productDTO.getImage())
                .brand(productDTO.getBrand())
                .category(productDTO.getCategory()).build();
    }

    public static ProductDTO convertProductRequestToDTO(final ProductRequest productRequest, final Category category) {
        return ProductDTO.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .isPromotion(productRequest.getIsPromotion())
                .discount(productRequest.getDiscount())
                .image(productRequest.getImage())
                .brand(productRequest.getBrand())
                .category(category).build();
    }

    public static ProductDTO convertProductRequestToDTO(final ProductRequest productRequest, final Category category, final ProductDTO productDTO) {
        return productDTO.toBuilder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .isPromotion(productRequest.getIsPromotion())
                .discount(productRequest.getDiscount())
                .image(productRequest.getImage())
                .brand(productRequest.getBrand())
                .category(category).build();
    }

    public static ProductDTO convertProductEntityToDTO(final Product product) {
        return ProductDTO.builder()
                .uuid(product.getUuid())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .isPromotion(product.getIsPromotion())
                .discount(product.getDiscount())
                .image(product.getImage())
                .brand(product.getBrand())
                .category(product.getCategory()).build();
    }

    public static ProductsListResponse convertProductDTOToListResponse(final ProductDTO productDTO) {
        return ProductsListResponse.builder()
                .uuid(productDTO.getUuid())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .isPromotion(productDTO.getIsPromotion())
                .discount(productDTO.getDiscount())
                .image(productDTO.getImage()).build();
    }

    public static ProductResponse convertProductDTOToResponse(final ProductDTO productDTO) {
        return ProductResponse.builder()
                .uuid(productDTO.getUuid())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .isPromotion(productDTO.getIsPromotion())
                .discount(productDTO.getDiscount())
                .image(productDTO.getImage())
                .category(productDTO.getCategory().getName())
                .brand(productDTO.getBrand()).build();
    }
}
