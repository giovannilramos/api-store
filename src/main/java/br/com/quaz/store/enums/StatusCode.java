package br.com.quaz.store.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    NOT_FOUND(404),
    UNAUTHORIZED(401),
    ALREADY_EXISTS(409);

    private final Integer statusCodeNumber;
    StatusCode(final Integer code) {
        this.statusCodeNumber = code;
    }
}
