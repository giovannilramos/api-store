package br.com.quaz.store;

import br.com.quaz.store.controllers.response.ProductsListResponse;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.services.GetWishListService;
import org.junit.jupiter.api.BeforeEach;
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
import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetWishListServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetWishListService getWishListService;
    private static final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaW92YW5uaWxyYW1vczU1QGdtYWlsLmNvbSIsImV4cCI6MTY3MTEwODg1MX0.jkFfvB1V04AreGq3mt-7bGqKsYtQN-mLaRsEds_OIRc";
    private Optional<User> userOptional;

    @BeforeEach
    void createUser() {
        this.userOptional = Optional.of(userMock(UUID.randomUUID(), "Giovanni Ramos",
                "giovannilramos55@gmail.com", "giovanni.ramos"));
    }

    @Test
    void shouldListAllProductsInWishList() {
        final var pageable = PageRequest.of(0, 8);

        final var productMock = productMock(UUID.randomUUID(), "Teclado Mecânico Gamer Hyperx Alloy Origins Core Switch Red Rgb", "Hyperx",
                BigDecimal.TEN, false, 0, "Teclado");

        final var productList = List.of(productMock);
        final var productPage = new PageImpl<>(productList);

        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(this.userOptional);
        when(productRepository.findAllByLoggedUser("giovannilramos55@gmail.com", pageable)).thenReturn(productPage);


        final var wishList = getWishListService.getWishList(token, pageable);

        assertEquals(1, wishList.size());
        assertNotNull(wishList);
    }

    @Test
    void shouldReturnAEmptyList() {
        final var pageable = PageRequest.of(0, 8);

        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(this.userOptional);
        when(productRepository.findAllByLoggedUser("giovannilramos55@gmail.com", pageable)).thenReturn(Page.empty());

        final var wishList = getWishListService.getWishList(token, pageable);

        assertEquals(0, wishList.size());
        assertEquals(new ArrayList<ProductsListResponse>(), wishList);
    }
}
