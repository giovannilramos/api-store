package br.com.quaz.store.exceptions;

public class NotFoundException extends Exceptions {
    public NotFoundException(final String msg, final Integer code) {
        super(msg, code);
    }
}
