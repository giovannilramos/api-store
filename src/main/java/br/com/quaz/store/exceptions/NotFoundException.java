package br.com.quaz.store.exceptions;

import br.com.quaz.store.enums.StatusCode;

public class NotFoundException extends Exceptions {
    public NotFoundException(final String msg) {
        super(msg, StatusCode.NOT_FOUND.getStatusCode());
    }
}
