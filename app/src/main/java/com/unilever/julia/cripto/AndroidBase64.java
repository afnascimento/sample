package com.unilever.julia.cripto;

import android.util.Base64;

public class AndroidBase64 implements IAESBase64 {

    //public static final int DEFAULT = android.util.Base64.DEFAULT;

    //public static final int NO_WRAP = android.util.Base64.NO_WRAP;

    @Override
    public String encodeBase64String(final byte[] binaryData) {
        return Base64.encodeToString(binaryData, Base64.NO_WRAP);
    }

    @Override
    public byte[] decodeBase64(final String base64String) {
        return Base64.decode(base64String, Base64.NO_WRAP);
    }
}