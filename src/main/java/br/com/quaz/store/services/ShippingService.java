package br.com.quaz.store.services;

import br.com.quaz.store.controllers.request.PurchaseRequest;
import br.com.quaz.store.controllers.response.ShippingResponse;
import br.com.quaz.store.entities.Product;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.helper.TotalAmountHelper;
import br.com.quaz.store.integrations.ShippingIntegration;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {
    private final ShippingIntegration shippingIntegration;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ShippingResponse> shippingList(final String jwtToken, final PurchaseRequest purchaseRequest) {
        final var address = addressRepository.findById(purchaseRequest.getAddressUuid())
                .orElseThrow(() ->
                        new NotFoundException("Address not found"));
        final var email = decoderTokenJwt(jwtToken);
        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        final var productList = new ArrayList<Product>();
        final var volumes = purchaseRequest.getProductList().stream().map(productPurchaseRequest -> {
            final var product = productRepository.findById(productPurchaseRequest.getProductUuid())
                    .orElseThrow(() -> new NotFoundException("Product not found"));
            productList.add(product);
            return "{\n" +
                    "              \"amount\": " + productPurchaseRequest.getQuantity() + ",\n" +
                    "              \"category\": \"31\",\n" +
                    "              \"height\": " + product.getHeight() + ",\n" +
                    "              \"width\":" + product.getWidth() + ",\n" +
                    "              \"length\": " + product.getLength() + ",\n" +
                    "              \"unitary_price\": " + product.getPrice() + ",\n" +
                    "              \"unitary_weight\": " + product.getWeight() + "\n" +
                    "            }\n";
        }).collect(Collectors.toList());

        final var mapper = new ObjectMapper();
        try {
            final var totalAmount = TotalAmountHelper.calculateTotalAmount(productList, purchaseRequest);
            final var shipping = "{\n" +
                    "      \"shipper\": {\n" +
                    "        \"registered_number\": \"48.929.920/0001-15\",\n" +
                    "        \"token\": \"\",\n" +
                    "        \"platform_code\": \"\"\n" +
                    "      },\n" +
                    "      \"recipient\": {\n" +
                    "        \"type\": 0,\n" +
                    "        \"registered_number\": \"" + user.getTaxId() + "\",\n" +
                    "        \"country\": \"BRA\",\n" +
                    "        \"zipcode\": " + address.getCep() + "\n" +
                    "      },\n" +
                    "      \"dispatchers\": [\n" +
                    "        {\n" +
                    "          \"registered_number\": \"48.929.920/0001-15\",\n" +
                    "          \"zipcode\": 13052570,\n" +
                    "          \"total_price\": " + totalAmount + ",\n" +
                    "          \"volumes\": " + volumes +
                    "        }\n" +
                    "      ],\n" +
                    "      \"limit\": 10,\n" +
                    "      \"simulation_type\": [\n" +
                    "        0\n" +
                    "      ]\n" +
                    "    }";

            final var jsonNode = shippingIntegration.getShipping(mapper.readTree(shipping));
            final var shippingResponseList = new ArrayList<ShippingResponse>();

            jsonNode.get("dispatchers").forEach(obj -> {
                final var shippingResponse = ShippingResponse.builder()
                        .days(obj.get("").bigIntegerValue())
                        .estimatedDate("")
                        .name("")
                        .value(null)
                        .build();
                shippingResponseList.add(shippingResponse);
            });
            return shippingResponseList;
        } catch (final Exception e) {
            log.error("Get shipping response error: {}", e.getMessage());
        }
        return new ArrayList<>();
    }
}
