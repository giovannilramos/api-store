package br.com.quaz.store.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
    private List<ProductPurchaseRequest> productList;
    private UUID addressUuid;
    private BigDecimal shipping;
}
