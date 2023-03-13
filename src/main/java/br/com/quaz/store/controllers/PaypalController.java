package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.request.PurchaseRequest;
import br.com.quaz.store.controllers.response.OrderResponse;
import br.com.quaz.store.services.purchase.PaypalCapturePaymentOrderService;
import br.com.quaz.store.services.purchase.PaypalCreateOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/paypal")
@RequiredArgsConstructor
public class PaypalController {
    private final PaypalCreateOrderService paypalCreateOrderService;
    private final PaypalCapturePaymentOrderService paypalCapturePaymentOrderService;

    @PostMapping("/order")
    @Transactional
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader(name = "Authorization") final String tokenJwt, @Valid @RequestBody final PurchaseRequest purchaseRequest) {
        return ResponseEntity.ok(paypalCreateOrderService.createOrder(tokenJwt, purchaseRequest));
    }

    @PostMapping("/order/complete/{id}")
    @Transactional
    public ResponseEntity<Void> capturePaymentOrder(@PathVariable("id") final String id) {
        paypalCapturePaymentOrderService.capturePaymentOrder(id);
        return ResponseEntity.noContent().build();
    }
}
