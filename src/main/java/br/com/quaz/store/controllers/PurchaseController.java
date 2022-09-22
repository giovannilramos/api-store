package br.com.quaz.store.controllers;

import br.com.quaz.store.request.PurchaseRequest;
import br.com.quaz.store.response.PurchaseResponse;
import br.com.quaz.store.services.ListPurchaseService;
import br.com.quaz.store.services.RegisterPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/purchase")
public class PurchaseController {
    private final ListPurchaseService listPurchaseService;
    private final RegisterPurchaseService registerPurchaseService;

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> find(@RequestHeader(name = "Authorization") final String jwtToken, @PageableDefault(direction = Sort.Direction.DESC) final Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(listPurchaseService.purchaseList(jwtToken, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> registerPurchase(@RequestHeader(name = "Authorization") final String jwtToken, @RequestBody final PurchaseRequest purchaseRequest) {
        registerPurchaseService.registerPurchase(jwtToken, purchaseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
