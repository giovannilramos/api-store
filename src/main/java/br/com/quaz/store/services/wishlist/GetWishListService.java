package br.com.quaz.store.services.wishlist;

import br.com.quaz.store.controllers.response.ProductsListResponse;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.repositories.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductDTOToListResponse;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductEntityToDTO;

@Service
@RequiredArgsConstructor
public class GetWishListService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ProductsListResponse> getWishList(final String jwtToken, final Pageable pageable) {
        final var email = decoderTokenJwt(jwtToken);

        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        final var wishListsProducts = wishListRepository.findAllByUser(user, pageable);

        return wishListsProducts.stream().map(wishList -> {
            final var productPage = productRepository.findById(wishList.getProduct().getUuid())
                    .orElseThrow(() -> new NotFoundException("Product not found"));
            return convertProductDTOToListResponse(convertProductEntityToDTO(productPage));
        }).toList();
    }
}
