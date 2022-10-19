package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.response.ProductsListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;

@Service
@RequiredArgsConstructor
public class GetWishListService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ProductsListResponse> getWishList(final String jwtToken, final Pageable pageable) {
        var sub = decoderTokenJwt(jwtToken);

        final var user = userRepository.findByEmail(sub)
                .orElseThrow(() -> new NotFoundException("User not found"));
        final var productList = productRepository.findAllByLoggedUser(user.getEmail(), pageable);

        return productList.stream().map(productPage -> ProductsListResponse.builder()
                .uuid(productPage.getUuid())
                .name(productPage.getName())
                .price(productPage.getPrice())
                .isPromotion(productPage.getIsPromotion())
                .discount(productPage.getDiscount())
                .image(productPage.getImage()).build()).collect(Collectors.toList());
    }
}
