package br.com.quaz.store.exceptions;

import br.com.quaz.store.enums.StatusCode;

public class BadRequestException extends BaseException {
    public BadRequestException(final String msg) {
        super(msg, StatusCode.BAD_REQUEST.getStatusCodeNumber());
    }
}
