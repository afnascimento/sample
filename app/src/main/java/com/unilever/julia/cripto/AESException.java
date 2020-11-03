package com.unilever.julia.cripto;

public class AESException extends Exception {

    public AESException() {
    }

    public AESException(String message) {
        super(message);
    }

    public AESException(Throwable cause) {
        super(cause);
    }

    public AESException(String message, Throwable cause) {
        super(message, cause);
    }
}
