package br.com.quaz.store.controllers.response;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.enums.PaypalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {
    private UUID uuid;
    private AddressResponse address;
    private String purchaseNumber;
    private BigDecimal totalAmount;
    private PaypalStatus status;
    private List<Product> productList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
