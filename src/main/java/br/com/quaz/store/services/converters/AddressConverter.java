package br.com.quaz.store.services.converters;

import br.com.quaz.store.entities.Address;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.request.AddressRequest;
import br.com.quaz.store.response.AddressResponse;
import br.com.quaz.store.services.dto.AddressDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressConverter {
    public static Address convertAddressDTOToEntity(final AddressDTO addressDTO) {
        return Address.builder()
                .uuid(addressDTO.getUuid())
                .cep(addressDTO.getCep())
                .number(addressDTO.getNumber())
                .street(addressDTO.getStreet())
                .district(addressDTO.getDistrict())
                .country(addressDTO.getCountry())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .complement(addressDTO.getComplement())
                .user(addressDTO.getUser()).build();
    }

    public static AddressDTO convertAddressRequestToDTO(final AddressRequest addressRequest, final User user) {
        return AddressDTO.builder()
                .cep(addressRequest.getCep())
                .number(addressRequest.getNumber())
                .street(addressRequest.getStreet())
                .district(addressRequest.getDistrict())
                .country(addressRequest.getCountry())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .complement(addressRequest.getComplement())
                .user(user).build();
    }

    public static AddressDTO convertAddressRequestToDTO(final AddressRequest addressRequest, final AddressDTO addressDTO) {
        return addressDTO.toBuilder()
                .cep(addressRequest.getCep())
                .number(addressRequest.getNumber())
                .street(addressRequest.getStreet())
                .district(addressRequest.getDistrict())
                .country(addressRequest.getCountry())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .complement(addressRequest.getComplement()).build();
    }

    public static AddressDTO convertAddressEntityToDTO(final Address address) {
        return AddressDTO.builder()
                .uuid(address.getUuid())
                .cep(address.getCep())
                .number(address.getNumber())
                .street(address.getStreet())
                .district(address.getDistrict())
                .country(address.getCountry())
                .city(address.getCity())
                .state(address.getState())
                .complement(address.getComplement())
                .user(address.getUser()).build();
    }

    public static AddressResponse convertAddressDTOToResponse(final AddressDTO addressDTO) {
        return AddressResponse.builder()
                .uuid(addressDTO.getUuid())
                .street(addressDTO.getStreet())
                .district(addressDTO.getDistrict())
                .city(addressDTO.getCity())
                .number(addressDTO.getNumber())
                .cep(addressDTO.getCep())
                .country(addressDTO.getCountry())
                .state(addressDTO.getState())
                .complement(addressDTO.getComplement())
                .build();
    }
}
