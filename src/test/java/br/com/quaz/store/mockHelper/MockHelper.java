package br.com.quaz.store.mockHelper;

import br.com.quaz.store.entities.Category;
import br.com.quaz.store.entities.Product;
import br.com.quaz.store.entities.Roles;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.enums.RoleName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockHelper {
    public static User userMock(final UUID uuid, final String name, final String email, final String username) {
        return new User(uuid, name, LocalDate.now(),
                email, username,
                new BCryptPasswordEncoder().encode("123"),
                setRolesMock(),
                LocalDateTime.now(), LocalDateTime.now());
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
}
