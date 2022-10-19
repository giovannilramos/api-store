package br.com.quaz.store.exceptions;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final Integer code;
    public BaseException(final String msg, final Integer code) {
        super(msg);
        this.code = code;
    }
}
