package br.com.quaz.store;

import br.com.quaz.store.controllers.response.ProductsListResponse;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.entities.WishList;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.repositories.WishListRepository;
import br.com.quaz.store.services.wishlist.GetWishListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.productMock;
import static br.com.quaz.store.mockHelper.MockHelper.token;
import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetWishListServiceTest {
    private final User user = userMock(UUID.randomUUID(), "Giovanni Ramos", "giovannilramos55@gmail.com", "giovanni.ramos");
    @Mock
    private WishListRepository wishListRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private GetWishListService getWishListService;

    @Test
    void shouldListAllProductsInWishList() {
        final var pageable = PageRequest.of(0, 8);

        final var productMock = productMock(UUID.randomUUID(), "Teclado Mecânico Gamer Hyperx Alloy Origins Core Switch Red Rgb", "Hyperx",
                BigDecimal.TEN, false, 0, "Teclado");
        final var wishListMock = WishList.builder()
                .uuid(UUID.randomUUID())
                .product(productMock)
                .user(user).build();
        final var wishList = List.of(wishListMock);
        final var wishListPage = new PageImpl<>(wishList);

        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(Optional.of(this.user));
        when(productRepository.findById(productMock.getUuid())).thenReturn(Optional.of(productMock));
        when(wishListRepository.findAllByUser(user, pageable)).thenReturn(wishListPage);


        final var wishListResponse = getWishListService.getWishList(token, pageable);

        assertEquals(1, wishListResponse.size());
        assertNotNull(wishList);
    }

    @Test
    void shouldReturnAEmptyList() {
        final var pageable = PageRequest.of(0, 8);

        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(Optional.of(this.user));
        when(wishListRepository.findAllByUser(user, pageable)).thenReturn(Page.empty());

        final var wishList = getWishListService.getWishList(token, pageable);

        assertEquals(0, wishList.size());
        assertEquals(new ArrayList<ProductsListResponse>(), wishList);
    }
}
