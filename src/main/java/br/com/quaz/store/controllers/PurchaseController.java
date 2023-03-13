package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.response.PurchaseResponse;
import br.com.quaz.store.services.purchase.ListPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {
    private final ListPurchaseService listPurchaseService;

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> find(@RequestHeader(name = "Authorization") final String jwtToken, @PageableDefault(direction = Sort.Direction.DESC) final Pageable pageable) {
        return ResponseEntity.ok(listPurchaseService.purchaseList(jwtToken, pageable));
    }
}
