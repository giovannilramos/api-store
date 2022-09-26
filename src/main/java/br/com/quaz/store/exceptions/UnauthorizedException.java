package br.com.quaz.store.exceptions;

import br.com.quaz.store.enums.StatusCode;

public class UnauthorizedException extends Exceptions {
    public UnauthorizedException(final String msg) {
        super(msg, StatusCode.UNAUTHORIZED.getStatusCode());
    }
}
