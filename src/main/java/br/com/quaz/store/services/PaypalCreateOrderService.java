package br.com.quaz.store.services;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    public OrderResponse createOrder(final String jwtToken,  final PurchaseRequest purchaseRequest) {
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
        final var productList = productRepository.findAllByUuidIn(purchaseRequest.getProductUuidList());



        final var paypalResponse = paypalIntegration.createOrder(purchaseRequest.getPaypalRequest());
        final var orderResponse = new OrderResponse();
        try {
            final var id = paypalResponse.get("id").asText();
            final var status = PaypalStatus.valueOf(paypalResponse.get("status").asText());
            final var link = paypalResponse.get("links").get(1).get("href").asText();
            final var purchase = new Purchase();
            final var totalAmount = productList.stream().map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

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
