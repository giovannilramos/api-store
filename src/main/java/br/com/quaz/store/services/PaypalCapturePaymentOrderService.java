package br.com.quaz.store.services;

import br.com.quaz.store.enums.PaypalStatus;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.integrations.PaypalIntegration;
import br.com.quaz.store.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaypalCapturePaymentOrderService {
    private final PaypalIntegration paypalIntegration;
    private final PurchaseRepository purchaseRepository;

    public void capturePaymentOrder(final String id) {
        final var purchase = purchaseRepository.findByPurchaseNumber(id)
                .orElseThrow(() -> new NotFoundException("Purchase not found"));
        final var orderStatus = paypalIntegration.getOrderStatus(id).get("status").asText();
        if (!orderStatus.equals(PaypalStatus.APPROVED.name())) {
            throw new IllegalStateException("Order not approved");
        }

        final var orderPayment = paypalIntegration.capturePaymentOrder(id);

        purchase.setStatus(PaypalStatus.valueOf(orderPayment.get("status").asText()));

        if (!purchase.getStatus().equals(PaypalStatus.COMPLETED)) {
            throw new IllegalStateException("Order not completed");
        }
        purchaseRepository.save(purchase);
    }
}
