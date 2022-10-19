package br.com.quaz.store.exceptions;

import br.com.quaz.store.enums.StatusCode;

public class AlreadyExistsException extends BaseException {
    public AlreadyExistsException(final String msg) {
        super(msg, StatusCode.ALREADY_EXISTS.getStatusCodeNumber());
    }
}
