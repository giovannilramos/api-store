package br.com.quaz.store.integrations;

import br.com.quaz.store.integrations.dto.ViaCepWebClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "viaCep", url = "${integrations.viaCep.url}")
public interface ViaCepIntegration {
    @GetMapping("/{cep}/json/")
    ViaCepWebClientResponse getCep(@PathVariable("cep") final String cep);

}
