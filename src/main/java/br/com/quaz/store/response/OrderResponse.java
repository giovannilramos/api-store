package br.com.quaz.store.response;

import br.com.quaz.store.enums.PaypalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String id;
    private PaypalStatus status;
    private String link;
}
