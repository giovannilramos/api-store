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

        var userOptional = userRepository.findByEmail(sub);
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(sub);
            if (userOptional.isEmpty()) {
                throw new NotFoundException("User not found");
            }
        }
        final var user = userOptional.get();

        final var productList = productRepository.findAllByLoggedUser(user.getEmail(), pageable);

        return productList.stream().map(productPage -> {
                    final var productsListResponse = new ProductsListResponse();
                    productsListResponse.setUuid(productPage.getUuid());
                    productsListResponse.setName(productPage.getName());
                    productsListResponse.setPrice(productPage.getPrice());
                    productsListResponse.setIsPromotion(productPage.getIsPromotion());
                    productsListResponse.setDiscount(productPage.getDiscount());
                    productsListResponse.setImage(productPage.getImage());
                    return productsListResponse;
                }).collect(Collectors.toList());
    }
}
