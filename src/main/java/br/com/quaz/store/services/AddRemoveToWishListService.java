package br.com.quaz.store.services;

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
        final var sub = decoderTokenJwt(jwtToken);
        var userOptional = userRepository.findByEmail(sub);

        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(sub);
            if (userOptional.isEmpty()) {
                throw new NotFoundException("User not found");
            }
        }
        final var user = userOptional.get();
        final var wishListExists = wishListRepository.existsWishListByUserAndProduct(user,product);

        if (wishListExists) {
            final var wishList = wishListRepository.findByUserAndProduct(userOptional.get(), product)
                    .orElseThrow(() -> new NotFoundException("Item not found"));
            wishListRepository.delete(wishList);
        } else {
            final var wishList = new WishList();

            wishList.setUser(user);
            wishList.setProduct(product);

            wishListRepository.save(wishList);
        }
    }
}
