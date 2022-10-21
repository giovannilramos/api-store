package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.request.AddressRequest;
import br.com.quaz.store.controllers.response.AddressResponse;
import br.com.quaz.store.services.CreateAddressService;
import br.com.quaz.store.services.DeleteAddressService;
import br.com.quaz.store.services.ListAddressService;
import br.com.quaz.store.services.UpdateAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final ListAddressService listAddressService;
    private final CreateAddressService createAddressService;
    private final DeleteAddressService deleteAddressService;
    private final UpdateAddressService updateAddressService;

    @GetMapping
    public ResponseEntity<List<AddressResponse>> listAddresses(@RequestHeader(name = "Authorization") final String jwtToken) {
        return ResponseEntity.ok(listAddressService.listAddresses(jwtToken));
    }

    @PostMapping
    public ResponseEntity<Void> createAddress(@RequestHeader(name = "Authorization") final String jwtToken, @RequestBody final AddressRequest addressRequest) {
        createAddressService.createAddress(jwtToken, addressRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAddress(@PathVariable("id") final UUID uuid, @RequestBody final AddressRequest addressRequest) {
        updateAddressService.updateAddress(uuid, addressRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") final UUID uuid) {
        deleteAddressService.deleteAddress(uuid);

        return ResponseEntity.noContent().build();
    }
}
