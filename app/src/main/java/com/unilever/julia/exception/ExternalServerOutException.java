package com.unilever.julia.exception;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Andre on 12/03/2018.
 */

public class ExternalServerOutException extends Exception {

    private String textTitle, textMessage, icon;

    public ExternalServerOutException(@NonNull String title, @NonNull String message, @NonNull String icon) {
        this.textTitle = title;
        this.textMessage = message;
        this.icon = icon;
    }

    @NonNull
    public String getTextTitle() {
        return StringUtils.trimToEmpty(textTitle);
    }

    @NonNull
    public String getTextMessage() {
        return StringUtils.trimToEmpty(textMessage);
    }

    @NonNull
    public String getIcon() {
        return StringUtils.trimToEmpty(icon);
    }
}
