package br.com.quaz.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private String userId;
    private String accessToken;
    private String refreshToken;
}
