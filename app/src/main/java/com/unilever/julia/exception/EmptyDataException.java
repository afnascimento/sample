package com.unilever.julia.exception;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Andre on 12/03/2018.
 */

public class EmptyDataException extends Exception {

    public EmptyDataException() {
    }

    private String title, description;

    public EmptyDataException(String message) {
        super(message);
        this.description = message;
    }

    public EmptyDataException(Throwable cause) {
        super(cause);
    }

    public EmptyDataException(String message, Throwable cause) {
        super(message, cause);
        this.description = message;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getTitle() {
        return StringUtils.trimToEmpty(title);
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getDescription() {
        return StringUtils.trimToEmpty(description);
    }
}
