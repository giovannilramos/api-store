package br.com.quaz.store.integrations;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "viaCep", url = "https://viacep.com.br/ws")
public interface ViaCepIntegration {
    @GetMapping("/{cep}/json/")
    JsonNode getCep(@PathVariable("cep") final String cep);

}
