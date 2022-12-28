package br.com.quaz.store.integrations;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "frete", url = "${integrations.shipping.url}")
public interface ShippingIntegration {
    @PostMapping("/api/v3/quote/simulate")
    JsonNode getShipping(@RequestBody final JsonNode jsonNode);
}
