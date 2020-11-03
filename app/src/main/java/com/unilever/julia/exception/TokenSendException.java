package com.unilever.julia.exception;

/**
 * Created by Andre on 12/03/2018.
 */

public class TokenSendException extends Exception {

    public TokenSendException() {
    }

    public TokenSendException(String message) {
        super(message);
    }

    public TokenSendException(Throwable cause) {
        super(cause);
    }

    public TokenSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
