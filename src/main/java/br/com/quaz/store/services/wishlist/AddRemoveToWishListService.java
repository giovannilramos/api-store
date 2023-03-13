package br.com.quaz.store.services.wishlist;

import br.com.quaz.store.entities.WishList;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.repositories.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;

@Service
@RequiredArgsConstructor
public class AddRemoveToWishListService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void addRemoveToWishList(final String jwtToken, final UUID productUuid) {
        final var product = productRepository.findById(productUuid)
                .orElseThrow(() ->
                        new NotFoundException("Product not found"));
        final var email = decoderTokenJwt(jwtToken);
        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        final var wishListExists = wishListRepository.existsWishListByUserAndProduct(user, product);

        if (Boolean.TRUE.equals(wishListExists)) {
            final var wishList = wishListRepository.findByUserAndProduct(user, product)
                    .orElseThrow(() -> new NotFoundException("Item not found"));
            wishListRepository.delete(wishList);
            return;
        }
        final var wishList = WishList.builder().user(user).product(product).build();

        wishListRepository.save(wishList);
    }
}
