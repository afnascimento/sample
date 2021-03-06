package com.unilever.julia.exception;

/**
 * Created by Andre on 12/03/2018.
 */

public class InternalServerException extends Exception {

    public InternalServerException() {
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
