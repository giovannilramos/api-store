package br.com.quaz.store;

import br.com.quaz.store.controllers.request.ProductPurchaseRequest;
import br.com.quaz.store.controllers.request.PurchaseRequest;
import br.com.quaz.store.entities.Purchase;
import br.com.quaz.store.enums.PaypalStatus;
import br.com.quaz.store.integrations.PaypalIntegration;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.PurchaseRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.services.PaypalCreateOrderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.addressMock;
import static br.com.quaz.store.mockHelper.MockHelper.productMock;
import static br.com.quaz.store.mockHelper.MockHelper.purchaseMock;
import static br.com.quaz.store.mockHelper.MockHelper.token;
import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaypalCreateOrderServiceTest {
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private PaypalIntegration paypalIntegration;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PaypalCreateOrderService paypalCreateOrderService;

    @Test
    @SneakyThrows
    void shouldCreatePurchaseAndPaypalOrder() {
        final var id = "14L698333P1789131";
        final var user = userMock(UUID.randomUUID(), "Giovanni", "giovannilramos55@gmail.com", "giovanni.ramos");
        final var address = addressMock(UUID.randomUUID(), user);
        final var product = productMock(UUID.randomUUID(), "Teclado", "Razer", BigDecimal.TEN, false, 0, "Teclado");
        final var purchase = purchaseMock(UUID.randomUUID(), id, user, PaypalStatus.CREATED);
        final var mapper = new ObjectMapper();
        final var jsonCreateOrderResponse = "{\n" +
                "    \"id\": \"14L698333P1789131\",\n" +
                "    \"intent\": \"CAPTURE\",\n" +
                "    \"status\": \"CREATED\",\n" +
                "    \"purchase_units\": [\n" +
                "        {\n" +
                "            \"reference_id\": \"default\",\n" +
                "            \"amount\": {\n" +
                "                \"currency_code\": \"BRL\",\n" +
                "                \"value\": \"200.00\",\n" +
                "                \"breakdown\": {\n" +
                "                    \"item_total\": {\n" +
                "                        \"currency_code\": \"BRL\",\n" +
                "                        \"value\": \"200.00\"\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"payee\": {\n" +
                "                \"email_address\": \"john_merchant@example.com\",\n" +
                "                \"merchant_id\": \"C7CYMKZDG8D6E\"\n" +
                "            },\n" +
                "            \"items\": [\n" +
                "                {\n" +
                "                    \"name\": \"T-Shirt\",\n" +
                "                    \"unit_amount\": {\n" +
                "                        \"currency_code\": \"BRL\",\n" +
                "                        \"value\": \"200.00\"\n" +
                "                    },\n" +
                "                    \"quantity\": \"1\",\n" +
                "                    \"description\": \"Green XL\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"create_time\": \"2022-12-19T14:34:09Z\",\n" +
                "    \"links\": [\n" +
                "        {\n" +
                "            \"href\": \"https://api.sandbox.paypal.com/v2/checkout/orders/14L698333P1789131\",\n" +
                "            \"rel\": \"self\",\n" +
                "            \"method\": \"GET\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"href\": \"https://www.sandbox.paypal.com/checkoutnow?token=14L698333P1789131\",\n" +
                "            \"rel\": \"approve\",\n" +
                "            \"method\": \"GET\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"href\": \"https://api.sandbox.paypal.com/v2/checkout/orders/14L698333P1789131\",\n" +
                "            \"rel\": \"update\",\n" +
                "            \"method\": \"PATCH\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"href\": \"https://api.sandbox.paypal.com/v2/checkout/orders/14L698333P1789131/capture\",\n" +
                "            \"rel\": \"capture\",\n" +
                "            \"method\": \"POST\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        when(addressRepository.findById(any(UUID.class))).thenReturn(Optional.of(address));
        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(Optional.of(user));
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        when(paypalIntegration.createOrder(any(JsonNode.class))).thenReturn(mapper.readTree(jsonCreateOrderResponse));
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        final var paypalCreateOrderResponse = paypalCreateOrderService.createOrder(token, PurchaseRequest
                .builder()
                .addressUuid(address.getUuid())
                .productList(
                        List.of(ProductPurchaseRequest
                                .builder()
                                .productUuid(product.getUuid())
                                .quantity(1L).build())
                ).shipping(BigDecimal.TEN).build());

        assertNotNull(paypalCreateOrderResponse);
        assertEquals(PaypalStatus.CREATED, paypalCreateOrderResponse.getStatus());
        assertEquals(id, paypalCreateOrderResponse.getId());
    }
}
