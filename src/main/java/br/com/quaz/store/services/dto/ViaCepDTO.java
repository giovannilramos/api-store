package br.com.quaz.store.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class ViaCepDTO {
    private String city;
    private String state;
    private String complement;
    private String street;
    private String district;
}
