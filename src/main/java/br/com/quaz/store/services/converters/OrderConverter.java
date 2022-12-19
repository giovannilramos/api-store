package br.com.quaz.store.services.converters;

import br.com.quaz.store.enums.PaypalStatus;
import br.com.quaz.store.controllers.response.OrderResponse;
import br.com.quaz.store.services.dto.OrderDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {
    public static OrderResponse convertOrderDTOToResponse(final OrderDTO orderDTO) {
        return OrderResponse.builder()
                .id(orderDTO.getId())
                .status(orderDTO.getStatus())
                .link(orderDTO.getLink()).build();
    }

    public static OrderDTO convertOrderRequestToDTO(final String id, final String link, final PaypalStatus status) {
        return OrderDTO.builder()
                .id(id)
                .status(status)
                .link(link).build();
    }
}
