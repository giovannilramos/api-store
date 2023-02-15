package br.com.quaz.store.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPurchaseRequest {
    @NotNull(message = "productUuid cannot be null")
    private UUID productUuid;
    @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be greater than 0")
    private Long quantity;
}
