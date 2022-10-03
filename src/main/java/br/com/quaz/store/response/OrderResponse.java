package br.com.quaz.store.response;

import br.com.quaz.store.enums.PaypalStatus;
import lombok.Data;

@Data
public class OrderResponse {
    private String id;
    private PaypalStatus status;
    private String link;
}
