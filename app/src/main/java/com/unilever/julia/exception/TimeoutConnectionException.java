package com.unilever.julia.exception;

/**
 * Created by Andre on 12/03/2018.
 */

public class TimeoutConnectionException extends Exception {

    public TimeoutConnectionException() {
    }

    public TimeoutConnectionException(String message) {
        super(message);
    }

    public TimeoutConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
