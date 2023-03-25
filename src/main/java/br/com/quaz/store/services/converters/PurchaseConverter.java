package br.com.quaz.store.services.converters;

import br.com.quaz.store.entities.Address;
import br.com.quaz.store.entities.Product;
import br.com.quaz.store.entities.Purchase;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.enums.PaypalStatus;
import br.com.quaz.store.controllers.response.PurchaseResponse;
import br.com.quaz.store.services.dto.PurchaseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static br.com.quaz.store.services.converters.AddressConverter.convertAddressDTOToResponse;
import static br.com.quaz.store.services.converters.AddressConverter.convertAddressEntityToDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseConverter {
    public static Purchase convertPurchaseDTOToEntity(final PurchaseDTO purchaseDTO) {
        return Purchase.builder()
                .address(purchaseDTO.getAddress())
                .productList(purchaseDTO.getProductList())
                .totalAmount(purchaseDTO.getTotalAmount())
                .user(purchaseDTO.getUser())
                .purchaseNumber(purchaseDTO.getPurchaseNumber())
                .status(purchaseDTO.getStatus()).build();
    }

    public static PurchaseDTO convertPurchaseRequestToDTO(final String purchaseNumber, final PaypalStatus status,
                                                          final Address address, final List<Product> productList,
                                                          final User user, final BigDecimal totalAmount) {
        return PurchaseDTO.builder()
                .purchaseNumber(purchaseNumber)
                .totalAmount(totalAmount)
                .status(status)
                .address(address)
                .user(user)
                .productList(productList).build();
    }

    public static PurchaseDTO convertPurchaseEntityToDTO(final Purchase purchase) {
        return PurchaseDTO.builder()
                .uuid(purchase.getUuid())
                .purchaseNumber(purchase.getPurchaseNumber())
                .totalAmount(purchase.getTotalAmount())
                .status(purchase.getStatus())
                .address(purchase.getAddress())
                .user(purchase.getUser())
                .productList(purchase.getProductList())
                .createdAt(purchase.getCreatedAt())
                .updatedAt(purchase.getUpdatedAt()).build();
    }

    public static PurchaseResponse convertPurchaseDTOToResponse(final PurchaseDTO purchaseDTO) {
        return PurchaseResponse.builder()
                .uuid(purchaseDTO.getUuid())
                .purchaseNumber(purchaseDTO.getPurchaseNumber())
                .address(convertAddressDTOToResponse(convertAddressEntityToDTO(purchaseDTO.getAddress())))
                .status(purchaseDTO.getStatus())
                .totalAmount(purchaseDTO.getTotalAmount())
                .productList(purchaseDTO.getProductList())
                .createdAt(purchaseDTO.getCreatedAt())
                .updatedAt(purchaseDTO.getUpdatedAt()).build();
    }
}
