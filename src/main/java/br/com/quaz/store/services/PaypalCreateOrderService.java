package br.com.quaz.store.services;

import br.com.quaz.store.dto.PaypalItems;
import br.com.quaz.store.dto.UnitAmount;
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
        var userOptional = userRepository.findByEmail(sub);

        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(sub);
            if (userOptional.isEmpty()) {
                throw new NotFoundException("User not found");
            }
        }
        final var productList = new ArrayList<Product>();
        final var items = purchaseRequest.getProductUuidList().stream().map(productRequest -> {
            final var product = productRepository.findById(productRequest)
                    .orElseThrow(() -> new NotFoundException("Product not found"));
            final var paypalItems = new PaypalItems();
            paypalItems.setName(product.getName());
            paypalItems.setDescription(product.getDescription());
            paypalItems.setQuantity("1");
            paypalItems.setUnitAmount(
                    new UnitAmount("BRL", product.getIsPromotion() ?
                            (product.getPrice().multiply(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(product.getDiscount())
                                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)))
                                    .setScale(2, RoundingMode.HALF_UP)).toString() :
                            product.getPrice().toString()));
            productList.add(product);
            return paypalItems;
        }).collect(Collectors.toList());

        final var orderResponse = new OrderResponse();
        final var mapper = new ObjectMapper();

        try {
            final var itemsMapped = mapper.writeValueAsString(items);
            final var totalAmount = productList.stream().map(product -> {
                        if (product.getIsPromotion()) {
                            product.setPrice(product.getPrice().multiply(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(product.getDiscount())
                                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)))
                                    .setScale(2, RoundingMode.HALF_UP));
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
            final var purchase = new Purchase();

            purchase.setAddress(address);
            purchase.setProductList(productList);
            purchase.setTotalAmount(totalAmount);
            purchase.setUser(userOptional.get());
            purchase.setPurchaseNumber(id);
            purchase.setStatus(status);

            purchaseRepository.save(purchase);

            orderResponse.setId(id);
            orderResponse.setStatus(status);
            orderResponse.setLink(link);

        } catch (Exception e) {
            log.error("Set order response error: {}", e.getMessage());
        }

        return orderResponse;
    }
}
