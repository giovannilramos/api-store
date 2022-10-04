package br.com.quaz.store.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PurchaseRequest {
    private List<UUID> productUuidList;
    private UUID addressUuid;
}
