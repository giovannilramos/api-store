package br.com.quaz.store.services;

import br.com.quaz.store.enums.StatusCode;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.PurchaseRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.response.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;

@Service
@RequiredArgsConstructor
public class ListPurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    public List<PurchaseResponse> purchaseList(final String jwtToken, final Pageable pageable) {
        final var sub = decoderTokenJwt(jwtToken);

        var userOptional = userRepository.findByEmail(sub);
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(sub);
            if (userOptional.isEmpty()) {
                throw new NotFoundException("User not found", StatusCode.NOT_FOUND.getStatusCode());
            }
        }

        final var user = userOptional.get();

        final var purchaseList = purchaseRepository.findAllByLoggedUser(user.getEmail(), pageable);

        return purchaseList.stream().map(purchase -> {
            final var purchaseResponse = new PurchaseResponse();
            purchaseResponse.setAddress(purchase.getAddress());
            purchaseResponse.setUuid(purchase.getUuid());
            purchaseResponse.setPurchaseNumber(purchase.getPurchaseNumber());
            purchaseResponse.setTotalAmount(purchase.getTotalAmount());
            purchaseResponse.setUser(purchase.getUser());
            purchaseResponse.setProductList(purchase.getProductList());
            purchaseResponse.setCreatedAt(purchase.getCreatedAt());
            purchaseResponse.setUpdatedAt(purchase.getUpdatedAt());

            return purchaseResponse;
        }).collect(Collectors.toList());
    }
}
