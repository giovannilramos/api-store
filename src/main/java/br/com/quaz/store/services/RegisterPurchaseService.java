package br.com.quaz.store.services;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.entities.Purchase;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.PurchaseRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;

@Service
@RequiredArgsConstructor
public class RegisterPurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void registerPurchase(final String jwtToken, final PurchaseRequest purchaseRequest) {
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

        final var purchase = new Purchase();
        final var totalAmount = productList.stream().map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        purchase.setAddress(address);
        purchase.setProductList(productList);
        purchase.setTotalAmount(totalAmount);
        purchase.setUser(userOptional.get());

        purchaseRepository.save(purchase);
    }
}
