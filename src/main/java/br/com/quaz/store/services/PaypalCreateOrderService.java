package br.com.quaz.store.services;

import br.com.quaz.store.services.dto.PaypalItemsDTO;
import br.com.quaz.store.services.dto.UnitAmountDTO;
import br.com.quaz.store.entities.Product;
import br.com.quaz.store.entities.Purchase;
import br.com.quaz.store.enums.PaypalStatus;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.integrations.PaypalIntegration;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.PurchaseRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.PurchaseRequest;
import br.com.quaz.store.response.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaypalCreateOrderService {
    private final PaypalIntegration paypalIntegration;
    private final PurchaseRepository purchaseRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderResponse createOrder(final String jwtToken, final PurchaseRequest purchaseRequest) {
        final var address = addressRepository.findById(purchaseRequest.getAddressUuid())
                .orElseThrow(() ->
                        new NotFoundException("Address not found"));
        final var sub = decoderTokenJwt(jwtToken);
        final var user = userRepository.findByEmail(sub)
                .orElseThrow(() -> new NotFoundException("User not found"));

        final var productList = new ArrayList<Product>();
        final var items = purchaseRequest.getProductUuidList().stream().map(productRequest -> {
            final var product = productRepository.findById(productRequest)
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            productList.add(product);

            return PaypalItemsDTO.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .quantity("1")
                    .unitAmountDTO(UnitAmountDTO.builder().currencyCode("BRL")
                            .value(Boolean.TRUE.equals(product.getIsPromotion()) ?
                                    (product
                                            .getPrice()
                                            .multiply(BigDecimal.valueOf(1)
                                                    .subtract(BigDecimal.valueOf(product.getDiscount())
                                                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)))
                                            .setScale(2, RoundingMode.HALF_UP))
                                            .toString()
                                    : product.getPrice().toString()).build()).build();
        }).collect(Collectors.toList());

        final var orderResponse = OrderResponse.builder().build();
        final var mapper = new ObjectMapper();

        try {
            final var itemsMapped = mapper.writeValueAsString(items);
            final var totalAmount = productList.stream().map(product -> {
                        if (Boolean.TRUE.equals(product.getIsPromotion())) {
                            product.toBuilder().price(product.getPrice().multiply(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(product.getDiscount())
                                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)))
                                    .setScale(2, RoundingMode.HALF_UP)).build();
                            return product.getPrice();
                        }
                        return product.getPrice();
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            final var paypalRequest = "{\n" +
                    "        \"intent\": \"CAPTURE\",\n" +
                    "        \"purchase_units\": [\n" +
                    "            {\n" +
                    "                \"items\": " + itemsMapped + ",\n" +
                    "                \"amount\": {\n" +
                    "                    \"currency_code\": \"BRL\",\n" +
                    "                    \"value\": " + totalAmount + ",\n" +
                    "                    \"breakdown\": {\n" +
                    "                        \"item_total\": {\n" +
                    "                            \"currency_code\": \"BRL\",\n" +
                    "                            \"value\": " + totalAmount + "\n" +
                    "                        }\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }";

            final var paypalResponse = paypalIntegration.createOrder(mapper.readTree(paypalRequest));

            final var id = paypalResponse.get("id").asText();
            final var status = PaypalStatus.valueOf(paypalResponse.get("status").asText());
            final var link = paypalResponse.get("links").get(1).get("href").asText();
            final var purchase = Purchase.builder()
                    .address(address)
                    .productList(productList)
                    .totalAmount(totalAmount)
                    .user(user)
                    .purchaseNumber(id)
                    .status(status).build();

            purchaseRepository.save(purchase);

            orderResponse.toBuilder().id(id).status(status).link(link).build();
        } catch (Exception e) {
            log.error("Set order response error: {}", e.getMessage());
        }

        return orderResponse;
    }
}
