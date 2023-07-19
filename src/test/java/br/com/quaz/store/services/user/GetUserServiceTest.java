package br.com.quaz.store.services.user;

import br.com.quaz.store.entities.User;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserService getUserService;

    @Test
    void shouldShowLoggedUserInformation() {
        final var uuid = UUID.randomUUID();
        final var userOptional = Optional.of(userMock(uuid, "Giovanni Ramos",
                "giovannilramos55@gmail.com", "giovanni.ramos"));

        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(userOptional);

        final var userResponse = getUserService.findLoggedUser("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaW92YW5uaWxyYW1vczU1QGdtYWlsLmNvbSIsImV4cCI6MTY3MTEwODg1MX0.jkFfvB1V04AreGq3mt-7bGqKsYtQN-mLaRsEds_OIRc");

        assertEquals(uuid, userResponse.getUuid());
        assertEquals("Giovanni Ramos", userResponse.getName());
        assertEquals("giovannilramos55@gmail.com", userResponse.getEmail());
        assertEquals(LocalDate.now(), userResponse.getBirthDate());
        assertEquals("giovanni.ramos", userResponse.getUsername());
    }

    @Test
    void shouldThrowsUserNotFoundException() {
        final Optional<User> optionalEmpty = Optional.empty();

        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(optionalEmpty);

        assertThrows(NotFoundException.class, () -> getUserService.findLoggedUser("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaW92YW5uaWxyYW1vczU1QGdtYWlsLmNvbSIsImV4cCI6MTY3MTEwODg1MX0.jkFfvB1V04AreGq3mt-7bGqKsYtQN-mLaRsEds_OIRc"), "User not found");
    }
}
