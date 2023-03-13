package br.com.quaz.store;

import br.com.quaz.store.entities.Product;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.entities.WishList;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.repositories.WishListRepository;
import br.com.quaz.store.services.wishlist.AddRemoveToWishListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.productMock;
import static br.com.quaz.store.mockHelper.MockHelper.token;
import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddRemoveToWishListServiceTest {
    private final UUID productUuid = UUID.randomUUID();
    private final Product product = productMock(productUuid, "Teclado", "Razer", BigDecimal.TEN, false, 0, "Teclado");
    private final WishList wishList = WishList.builder().product(this.product).user(this.user).build();
    private final User user = userMock(UUID.randomUUID(), "Giovanni Ramos", "giovannilramos55@gmail.com", "giovanni.ramos");
    @Mock
    private WishListRepository wishListRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AddRemoveToWishListService addRemoveToWishListService;

    @Test
    void shouldAddToWishList() {
        when(productRepository.findById(this.productUuid)).thenReturn(Optional.of(this.product));
        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(Optional.of(this.user));
        when(wishListRepository.existsWishListByUserAndProduct(this.user, this.product)).thenReturn(Boolean.FALSE);
        when(wishListRepository.save(any())).thenReturn(wishList);

        addRemoveToWishListService.addRemoveToWishList(token, this.productUuid);

        verify(wishListRepository, times(1)).save(any());
        verify(wishListRepository, times(0)).delete(any());
        assertDoesNotThrow(() -> addRemoveToWishListService.addRemoveToWishList(token, this.productUuid));
    }

    @Test
    void shouldRemoveFromWishList() {
        when(productRepository.findById(this.productUuid)).thenReturn(Optional.of(this.product));
        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(Optional.of(this.user));
        when(wishListRepository.existsWishListByUserAndProduct(this.user, this.product)).thenReturn(Boolean.TRUE);
        when(wishListRepository.findByUserAndProduct(this.user, this.product)).thenReturn(Optional.of(this.wishList));

        addRemoveToWishListService.addRemoveToWishList(token, this.productUuid);

        verify(wishListRepository, times(0)).save(any());
        verify(wishListRepository, times(1)).delete(any());
        assertDoesNotThrow(() -> addRemoveToWishListService.addRemoveToWishList(token, this.productUuid));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserNotLoggedIn() {
        when(productRepository.findById(this.productUuid)).thenReturn(Optional.of(this.product));
        assertThrows(NotFoundException.class, () -> addRemoveToWishListService.addRemoveToWishList(token, this.productUuid),
                "User not found");
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenProductDontExists() {
        assertThrows(NotFoundException.class, () -> addRemoveToWishListService.addRemoveToWishList(token, this.productUuid),
                "Product not found");
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenTryToRemoveFromWishList() {
        when(productRepository.findById(this.productUuid)).thenReturn(Optional.of(this.product));
        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(Optional.of(this.user));
        when(wishListRepository.existsWishListByUserAndProduct(this.user, this.product)).thenReturn(Boolean.TRUE);

        assertThrows(NotFoundException.class, () -> addRemoveToWishListService.addRemoveToWishList(token, this.productUuid),
                "Item not found");
    }
}
