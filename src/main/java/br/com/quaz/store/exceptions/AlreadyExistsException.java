package br.com.quaz.store.exceptions;

import br.com.quaz.store.enums.StatusCode;

public class AlreadyExistsException extends Exceptions {
    public AlreadyExistsException(final String msg) {
        super(msg, StatusCode.ALREADY_EXISTS.getStatusCode());
    }
}
