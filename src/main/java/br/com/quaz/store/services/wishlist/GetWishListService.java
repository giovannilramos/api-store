package br.com.quaz.store.services.wishlist;

import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.ProductRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.controllers.response.ProductsListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.quaz.store.helper.UserHelper.decoderTokenJwt;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductDTOToListResponse;
import static br.com.quaz.store.services.converters.ProductConverter.convertProductEntityToDTO;

@Service
@RequiredArgsConstructor
public class GetWishListService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ProductsListResponse> getWishList(final String jwtToken, final Pageable pageable) {
        final var email = decoderTokenJwt(jwtToken);

        final var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        final var productList = productRepository.findAllByLoggedUser(user.getEmail(), pageable);

        return productList.stream().map(productPage ->
                        convertProductDTOToListResponse(convertProductEntityToDTO(productPage)))
                .collect(Collectors.toList());
    }
}
