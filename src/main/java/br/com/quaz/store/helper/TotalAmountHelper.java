package br.com.quaz.store.helper;

import br.com.quaz.store.controllers.request.PurchaseRequest;
import br.com.quaz.store.entities.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TotalAmountHelper {
    public static BigDecimal calculateTotalAmount(final List<Product> productList, final PurchaseRequest purchaseRequest) {
        return productList.stream().map(product -> {
                    if (Boolean.TRUE.equals(product.getIsPromotion())) {
                        product.toBuilder()
                                .price(product.getPrice()
                                        .multiply(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(product.getDiscount())
                                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)))
                                        .setScale(2, RoundingMode.HALF_UP)).build();
                        return product.getPrice().multiply(BigDecimal.valueOf(
                                purchaseRequest.getProductList().stream()
                                        .filter(f -> f.getProductUuid().equals(product.getUuid()))
                                        .findFirst().orElseThrow().getQuantity()
                        ));
                    }
                    return product.getPrice().multiply(BigDecimal.valueOf(
                            purchaseRequest.getProductList().stream()
                                    .filter(f -> f.getProductUuid().equals(product.getUuid()))
                                    .findFirst().orElseThrow().getQuantity()
                    ));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
