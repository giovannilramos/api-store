package br.com.quaz.store.exceptions;

public class UnauthorizedExistsException extends Exceptions {
    public UnauthorizedExistsException(final String msg, final Integer code) {
        super(msg, code);
    }
}
