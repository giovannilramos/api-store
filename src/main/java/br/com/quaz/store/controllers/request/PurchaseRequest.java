package br.com.quaz.store.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
    @NotEmpty(message = "productList cannot be empty")
    @Valid
    private List<ProductPurchaseRequest> productList;
    @NotNull(message = "addressUuid cannot be null")
    private UUID addressUuid;
    @NotNull(message = "shipping cannot be null")
    @PositiveOrZero(message = "shipping must be greater than or equal to 0")
    private BigDecimal shipping;
}
