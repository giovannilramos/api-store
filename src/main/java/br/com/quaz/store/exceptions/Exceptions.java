package br.com.quaz.store.exceptions;

import lombok.Getter;

@Getter
public class Exceptions extends RuntimeException {
    private final Integer code;
    public Exceptions(final String msg, final Integer code) {
        super(msg);
        this.code = code;
    }
}
