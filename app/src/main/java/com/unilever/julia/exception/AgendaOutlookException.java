package com.unilever.julia.exception;

/**
 * Created by Andre on 12/03/2018.
 */

public class AgendaOutlookException extends Exception {

    public AgendaOutlookException() {
    }

    public AgendaOutlookException(String message) {
        super(message);
    }

    public AgendaOutlookException(Throwable cause) {
        super(cause);
    }

    public AgendaOutlookException(String message, Throwable cause) {
        super(message, cause);
    }
}
