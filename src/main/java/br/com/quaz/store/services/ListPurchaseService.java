package br.com.quaz.store.services;

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
        final var email = decoderTokenJwt(jwtToken);

        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        final var purchaseList = purchaseRepository.findAllByLoggedUser(user.getEmail(), pageable);

        return purchaseList.stream().map(purchase -> PurchaseResponse.builder()
                .address(purchase.getAddress())
                .uuid(purchase.getUuid())
                .purchaseNumber(purchase.getPurchaseNumber())
                .status(purchase.getStatus())
                .totalAmount(purchase.getTotalAmount())
                .user(purchase.getUser())
                .productList(purchase.getProductList())
                .createdAt(purchase.getCreatedAt())
                .updatedAt(purchase.getUpdatedAt()).build()).collect(Collectors.toList());
    }
}
