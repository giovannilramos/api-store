package br.com.quaz.store.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtPayloadDTO {
    private String sub;
    private String iss;
    private String exp;
}
