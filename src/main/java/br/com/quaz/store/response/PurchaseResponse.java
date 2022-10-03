package br.com.quaz.store.response;

import br.com.quaz.store.entities.Address;
import br.com.quaz.store.entities.Product;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.enums.PaypalStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PurchaseResponse {
    /*TODO: Estágios da compra*/
    /*private Map<String, String> status;*/
    private Address address;
    private UUID uuid;
    private String purchaseNumber;
    private BigDecimal totalAmount;
    private PaypalStatus status;
    private User user;
    private List<Product> productList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
