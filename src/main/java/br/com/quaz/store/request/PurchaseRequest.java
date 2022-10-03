package br.com.quaz.store.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PurchaseRequest {
    private List<UUID> productUuidList;
    private UUID addressUuid;
    private JsonNode paypalRequest;
}
