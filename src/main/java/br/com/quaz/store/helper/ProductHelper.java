package br.com.quaz.store.helper;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.request.ProductRequest;

public class ProductHelper {
    public static void setProductEntity(final Product product, final ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setIsPromotion(productRequest.getIsPromotion());
        product.setDiscount(productRequest.getDiscount());
        product.setImage(productRequest.getImage());
        product.setCategory(productRequest.getCategory());
    }
}
