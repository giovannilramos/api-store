//package br.com.quaz.store;
//
//import br.com.quaz.store.integrations.PaypalIntegration;
//import br.com.quaz.store.repositories.PurchaseRepository;
//import br.com.quaz.store.services.purchase.PaypalCapturePaymentOrderService;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static br.com.quaz.store.mockHelper.MockHelper.purchaseMock;
//import static br.com.quaz.store.mockHelper.MockHelper.userMock;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class PaypalCapturePaymentOrderServiceTest {
//    @Mock
//    private PaypalIntegration paypalIntegration;
//    @Mock
//    private PurchaseRepository purchaseRepository;
//
//    @InjectMocks
//    private PaypalCapturePaymentOrderService paypalCapturePaymentOrderService;
//
//    private String id;
//
//    @Test
//    @SneakyThrows
//    void shouldUpdateAnPurchaseAndFinalizePaypalPaymentOrder() {
//        final var user = userMock(UUID.randomUUID(), "Giovanni", "giovannilramos55@gmail.com", "giovanni.ramos");
//        final var purchase = purchaseMock(UUID.randomUUID(), user);
//
//        when(purchaseRepository.findByPurchaseNumber(this.id)).thenReturn(Optional.of(purchase));
//
//        assertDoesNotThrow(() -> paypalCapturePaymentOrderService.capturePaymentOrder(this.id));
//    }
//}
