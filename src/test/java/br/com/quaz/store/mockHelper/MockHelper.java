package br.com.quaz.store.mockHelper;

import br.com.quaz.store.entities.Address;
import br.com.quaz.store.entities.Category;
import br.com.quaz.store.entities.Product;
import br.com.quaz.store.entities.Purchase;
import br.com.quaz.store.entities.Roles;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.enums.PaypalStatus;
import br.com.quaz.store.enums.RoleName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockHelper {
    public static final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaW92YW5uaWxyYW1vczU1QGdtYWlsLmNvbSIsImV4cCI6MTY3MTEwODg1MX0.jkFfvB1V04AreGq3mt-7bGqKsYtQN-mLaRsEds_OIRc";

    public static User userMock(final UUID uuid, final String name, final String email, final String username) {
        return User.builder()
                .uuid(uuid)
                .name(name)
                .taxId("12345678910")
                .email(email)
                .password(new BCryptPasswordEncoder().encode("123"))
                .roles(setRolesMock())
                .birthDate(LocalDate.now())
                .username(username)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
    }

    public static Address addressMock(final UUID uuid, final User user) {
        return Address.builder().uuid(uuid).user(user).cep("12345678").number("1")
                .street("Rua 1").district("Bairro 1").country("País 1").city("Cidade 1")
                .street("SP").complement("").build();
    }

    public static Set<Roles> setRolesMock() {
        return Stream.of(roleMock(RoleName.ROLE_ADMIN), roleMock(RoleName.ROLE_USER)).collect(Collectors.toSet());
    }

    public static Roles roleMock(final RoleName roleName) {
        return Roles.builder().uuid(UUID.randomUUID()).roleName(roleName)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
    }

    public static Category categoryMock(final String name) {
        return Category.builder().uuid(UUID.randomUUID()).name(name)
                .createdAt(LocalDateTime.now()).build();
    }

    public static Product productMock(final UUID uuid, final String name, final String brand,
                                      final BigDecimal price, final Boolean promotion,
                                      final Integer discount, final String categoryName) {
        return Product.builder().uuid(uuid).name(name).brand(brand).description("Quality product")
                .price(price).isPromotion(promotion).discount(discount).image("https://google.com")
                .category(categoryMock(categoryName)).createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
    }

    public static Purchase purchaseMock(final UUID uuid, final String purchaseNumber, final User user, final PaypalStatus status) {
        final var product = productMock(UUID.randomUUID(), "Teclado", "Razer", BigDecimal.TEN, false, 0, "Teclado");
        return Purchase.builder().uuid(uuid).purchaseNumber(purchaseNumber).totalAmount(BigDecimal.TEN)
                .status(status).address(addressMock(UUID.randomUUID(), user)).user(user)
                .productList(List.of(product)).createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
    }
}
