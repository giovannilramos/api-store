package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.response.ViaCepResponse;
import br.com.quaz.store.services.viacep.ViaCepApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cep")
public class ViaCepApiController {
    private final ViaCepApiService viaCepApiService;

    @GetMapping("/{cep}")
    public ResponseEntity<ViaCepResponse> getAddressByCep(@PathVariable("cep") final String cep) {
        return ResponseEntity.ok(viaCepApiService.getAddressByCep(cep));
    }

}
