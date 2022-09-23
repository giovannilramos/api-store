package br.com.quaz.store.exceptions;

public class UnauthorizedException extends Exceptions {
    public UnauthorizedException(final String msg, final Integer code) {
        super(msg, code);
    }
}
