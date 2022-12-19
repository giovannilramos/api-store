package br.com.quaz.store.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    NOT_ACCEPTABLE(406),
    ALREADY_EXISTS(409);

    private final Integer statusCodeNumber;
    StatusCode(final Integer code) {
        this.statusCodeNumber = code;
    }
}
