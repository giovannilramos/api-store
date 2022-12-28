package br.com.quaz.store.integrations;

import br.com.quaz.store.config.paypal.PaypalConfig;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "paypal", url = "${integrations.paypal.sandbox-base-url}", configuration = PaypalConfig.class)
public interface PaypalIntegration {
    @PostMapping("/v2/checkout/orders")
    JsonNode createOrder(@RequestBody final JsonNode jsonNode);

    @GetMapping("/v2/checkout/orders/{order_id}")
    JsonNode getOrderStatus(@PathVariable("order_id") final String order_id);

    @PostMapping("/v2/checkout/orders/{order_id}/capture")
    JsonNode capturePaymentOrder(@PathVariable("order_id") final String order_id);

}
