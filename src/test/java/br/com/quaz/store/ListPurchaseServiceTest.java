package br.com.quaz.store;

import br.com.quaz.store.enums.PaypalStatus;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.PurchaseRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.services.ListPurchaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.purchaseMock;
import static br.com.quaz.store.mockHelper.MockHelper.token;
import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListPurchaseServiceTest {
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ListPurchaseService listPurchaseService;

    @Test
    void shouldShowAllPurchasesMadeByLoggedUser() {
        final var pageable = PageRequest.of(0, 10);
        final var user = userMock(UUID.randomUUID(), "Giovanni", "giovannilramos55@gmail.com", "giovanni.ramos");
        final var purchase = purchaseMock(UUID.randomUUID(), String.valueOf(Math.random()), user, PaypalStatus.COMPLETED);
        when(purchaseRepository.findAllByLoggedUser("giovannilramos55@gmail.com", pageable)).thenReturn(List.of(purchase));
        when(userRepository.findByEmail("giovannilramos55@gmail.com")).thenReturn(Optional.of(user));

        final var purchaseResponse = listPurchaseService.purchaseList(token, pageable);

        assertEquals(1, purchaseResponse.size());
        assertNotNull(purchaseResponse);
    }

    @Test
    void shouldThrowsUserNotFoundException() {
        final var pageable = PageRequest.of(0, 10);
        assertThrows(NotFoundException.class, () -> listPurchaseService.purchaseList(token, pageable), "User not found");

    }
}
