package br.com.quaz.store.dto;

import lombok.Data;

@Data
public class JwtPayload {
    private String sub;
    private String exp;
}
