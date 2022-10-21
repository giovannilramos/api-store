package br.com.quaz.store.services.dto;

import br.com.quaz.store.entities.Address;
import br.com.quaz.store.entities.Product;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.enums.PaypalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class PurchaseDTO {
    private UUID uuid;
    private String purchaseNumber;
    private BigDecimal totalAmount;
    private PaypalStatus status;
    private Address address;
    private User user;
    private List<Product> productList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
