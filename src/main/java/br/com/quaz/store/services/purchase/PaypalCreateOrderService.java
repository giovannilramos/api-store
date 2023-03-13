package br.com.quaz.store.services.purchase;

import br.com.quaz.store.controllers.request.PurchaseRequest;
import br.com.quaz.store.controllers.response.OrderResponse;
import br.com.quaz.store.entities.Product;
import br.com.quaz.store.enums.PaypalStatus;
import br.com.quaz.store.enums.StatusCode;
import br.com.quaz.store.exceptions.BadRequestException;
import br.com.quaz.store.exceptions.BaseException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.helper.TotalAmountHelper;
import br.com.quaz.store.integrations.PaypalIntegration;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.PurchaseRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.services.dto.PaypalItemsDTO;
import br.com.quaz.store.services.dto.UnitAmountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;
import static br.com.quaz.store.services.converters.OrderConverter.convertOrderDTOToResponse;
import static br.com.quaz.store.services.converters.OrderConverter.convertOrderRequestToDTO;
import static br.com.quaz.store.services.converters.PurchaseConverter.convertPurchaseDTOToEntity;
import static br.com.quaz.store.services.converters.PurchaseConverter.convertPurchaseRequestToDTO;

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
        if (purchaseRequest.getProductList().stream().anyMatch(productPurchaseRequest -> productPurchaseRequest.getQuantity() <= 0)) {
            throw new BadRequestException("Quantity cannot be less than or equal to 0");
        }
        final var address = addressRepository.findById(purchaseRequest.getAddressUuid())
                .orElseThrow(() ->
                        new NotFoundException("Address not found"));
        final var email = decoderTokenJwt(jwtToken);
        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        final var productList = new ArrayList<Product>();
        final var items = purchaseRequest.getProductList().stream().map(productPurchaseRequest -> {
            final var product = productRepository.findById(productPurchaseRequest.getProductUuid())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            productList.add(product);

            return PaypalItemsDTO.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .quantity(String.valueOf(productPurchaseRequest.getQuantity()))
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
        items.add(PaypalItemsDTO.builder()
                .name("Frete")
                .quantity("1")
                .unitAmountDTO(UnitAmountDTO.builder()
                        .currencyCode("BRL")
                        .value(String.valueOf(purchaseRequest.getShipping()))
                        .build())
                .build());

        final var mapper = new ObjectMapper();

        try {
            final var itemsMapped = mapper.writeValueAsString(items);
            final var totalAmount = TotalAmountHelper.calculateTotalAmount(productList, purchaseRequest).add(purchaseRequest.getShipping());

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

            purchaseRepository.save(convertPurchaseDTOToEntity(convertPurchaseRequestToDTO(id, status, address, productList, user, totalAmount)));

            return convertOrderDTOToResponse(convertOrderRequestToDTO(id, link, status));
        } catch (final Exception e) {
            log.error("Set order response error: {}", e.getMessage());
        }

        throw new BaseException("Create order error", StatusCode.NOT_ACCEPTABLE.getStatusCodeNumber());
    }
}
