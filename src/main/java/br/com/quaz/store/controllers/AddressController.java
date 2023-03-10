package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.request.AddressRequest;
import br.com.quaz.store.controllers.response.AddressResponse;
import br.com.quaz.store.services.*;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static br.com.quaz.store.helper.UriHelper.getUri;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final ListAddressService listAddressService;
    private final CreateAddressService createAddressService;
    private final DeleteAddressService deleteAddressService;
    private final UpdateAddressService updateAddressService;
    private final GetAddressService getAddressService;

    @GetMapping
    public ResponseEntity<List<AddressResponse>> listAddresses(@RequestHeader(name = "Authorization") final String jwtToken) {
        return ResponseEntity.ok(listAddressService.listAddresses(jwtToken));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@RequestHeader(name = "Authorization") final String jwtToken,
                                                          @PathVariable("id") final UUID uuid) {
        return ResponseEntity.ok(getAddressService.getAddressById(uuid, jwtToken));
    }

    @PostMapping
    public ResponseEntity<Void> createAddress(@RequestHeader(name = "Authorization") final String jwtToken,
                                              @Valid @RequestBody final AddressRequest addressRequest,
                                              final UriComponentsBuilder uriComponentsBuilder) {
        final var addressResponse = createAddressService.createAddress(jwtToken, addressRequest);
        final var uri = getUri(uriComponentsBuilder, "/address/{id}", addressResponse.getUuid());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAddress(@PathVariable("id") final UUID uuid, @Valid @RequestBody final AddressRequest addressRequest) {
        updateAddressService.updateAddress(uuid, addressRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") final UUID uuid) {
        deleteAddressService.deleteAddress(uuid);

        return ResponseEntity.noContent().build();
    }
}
