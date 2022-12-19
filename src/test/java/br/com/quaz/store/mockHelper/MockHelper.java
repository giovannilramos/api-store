package br.com.quaz.store.mockHelper;

import br.com.quaz.store.entities.*;
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
        return new User(uuid, name, LocalDate.now(),
                email, username,
                new BCryptPasswordEncoder().encode("123"),
                setRolesMock(),
                LocalDateTime.now(), LocalDateTime.now());
    }

    public static Address addressMock(final UUID uuid, final User user) {
        return new Address(uuid, "12345678", "1", "Rua 1", "Bairro 1", "País 1", "Cidade 1", "SP", "", user);
    }

    public static Set<Roles> setRolesMock() {
        return Stream.of(roleMock(RoleName.ROLE_ADMIN), roleMock(RoleName.ROLE_USER)).collect(Collectors.toSet());
    }

    public static Roles roleMock(final RoleName roleName) {
        return new Roles(UUID.randomUUID(), roleName, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Category categoryMock(final String name) {
        return new Category(UUID.randomUUID(), name, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Product productMock(final UUID uuid, final String name, final String brand, final BigDecimal price,
                                      final Boolean promotion, final Integer discount,
                                      final String categoryName) {
        return new Product(uuid, name, brand,
                "Quality product", price, promotion, discount,
                "https://google.com", categoryMock(categoryName), LocalDateTime.now(),
                LocalDateTime.now());
    }

    public static Purchase purchaseMock(final UUID uuid, final String purchaseNumber, final User user, final PaypalStatus status) {
        final var product = productMock(UUID.randomUUID(), "Teclado", "Razer", BigDecimal.TEN, false, 0, "Teclado");
        return new Purchase(uuid, purchaseNumber, BigDecimal.TEN, status,
                addressMock(UUID.randomUUID(), user),
                user, List.of(product), LocalDateTime.now(), LocalDateTime.now());
    }
}
