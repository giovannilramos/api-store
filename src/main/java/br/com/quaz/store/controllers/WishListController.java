package br.com.quaz.store.controllers;

import br.com.quaz.store.response.ProductsListResponse;
import br.com.quaz.store.services.AddRemoveToWishListService;
import br.com.quaz.store.services.GetWishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/wish-list")
public class WishListController {
    private final AddRemoveToWishListService addRemoveToWishListService;
    private final GetWishListService getWishListService;

    @PostMapping
    public ResponseEntity<Void> addRemoveToWishList(@RequestHeader("Authorization") final String jwtToken, @RequestParam final String productUuid) {
        addRemoveToWishListService.addRemoveToWishList(jwtToken, UUID.fromString(productUuid));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductsListResponse>> findUser(@RequestHeader(name = "Authorization") final String jwtToken, @PageableDefault(direction = Sort.Direction.DESC) final Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(getWishListService.getWishList(jwtToken, pageable));
    }
}
