package br.com.quaz.store.controllers;

import br.com.quaz.store.request.PurchaseRequest;
import br.com.quaz.store.response.OrderResponse;
import br.com.quaz.store.services.PaypalCreateOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paypal")
@CrossOrigin(maxAge = 3600, origins = "*")
@RequiredArgsConstructor
public class PaypalController {
    private final PaypalCreateOrderService paypalCreateOrderService;
//    private final PaypalConfirmPaymentOrderService paypalConfirmPaymentOrderService;

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader(name = "Authorization") final String tokenJwt, @RequestBody final PurchaseRequest purchaseRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(paypalCreateOrderService.createOrder(tokenJwt, purchaseRequest));
    }

//    @PostMapping("/order/complete")
//    public ResponseEntity<OrderResponse> capturePaymentOrder(@RequestBody final JsonNode jsonNode) {
//        return ResponseEntity.status(HttpStatus.OK).body(paypalConfirmPaymentOrderService.paypalConfirmPaymentOrder(jsonNode));
//    }
}
