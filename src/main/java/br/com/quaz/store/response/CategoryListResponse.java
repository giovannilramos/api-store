package br.com.quaz.store.response;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryListResponse {
    private UUID uuid;
    private String name;
}
