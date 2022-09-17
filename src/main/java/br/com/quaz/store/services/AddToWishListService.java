package br.com.quaz.store.services;

import br.com.quaz.store.repositories.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddToWishListService {
    private final WishListRepository wishListRepository;


}
