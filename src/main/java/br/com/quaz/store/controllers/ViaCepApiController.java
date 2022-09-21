package br.com.quaz.store.controllers;

import br.com.quaz.store.response.AddressResponse;
import br.com.quaz.store.services.ViaCepApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/cep")
public class ViaCepApiController {
    private final ViaCepApiService viaCepApiService;

    @GetMapping("/{cep}")
    public ResponseEntity<AddressResponse> getAddressByCep(@PathVariable("cep") final String cep) {
        return ResponseEntity.status(HttpStatus.OK).body(viaCepApiService.getAddressByCep(cep));
    }

}
