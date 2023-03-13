package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.response.ProductsListResponse;
import br.com.quaz.store.services.wishlist.AddRemoveToWishListService;
import br.com.quaz.store.services.wishlist.GetWishListService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wish-list")
public class WishListController {
    private final AddRemoveToWishListService addRemoveToWishListService;
    private final GetWishListService getWishListService;

    @PatchMapping
    @Transactional
    public ResponseEntity<Void> addRemoveToWishList(@RequestHeader("Authorization") final String jwtToken, @Valid @RequestBody final JsonNode productUuid) {
        addRemoveToWishListService.addRemoveToWishList(jwtToken, UUID.fromString(productUuid.get("productUuid").asText()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductsListResponse>> findUser(@RequestHeader(name = "Authorization") final String jwtToken, @PageableDefault(direction = Sort.Direction.DESC) final Pageable pageable) {
        return ResponseEntity.ok(getWishListService.getWishList(jwtToken, pageable));
    }
}
