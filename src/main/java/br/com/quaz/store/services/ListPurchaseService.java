package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.PurchaseRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.controllers.response.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;
import static br.com.quaz.store.services.converters.PurchaseConverter.convertPurchaseDTOToResponse;
import static br.com.quaz.store.services.converters.PurchaseConverter.convertPurchaseEntityToDTO;

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

        return purchaseList.stream().map(purchase ->
                        convertPurchaseDTOToResponse(convertPurchaseEntityToDTO(purchase))
                ).collect(Collectors.toList());
    }
}
