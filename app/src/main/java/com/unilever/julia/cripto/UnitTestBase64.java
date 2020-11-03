package com.unilever.julia.cripto;

import com.google.crypto.tink.subtle.Base64;

public class UnitTestBase64 implements IAESBase64 {

    @Override
    public String encodeBase64String(final byte[] binaryData) {
        return Base64.encodeToString(binaryData, Base64.NO_WRAP);
    }

    @Override
    public byte[] decodeBase64(final String base64String) {
        return Base64.decode(base64String, Base64.NO_WRAP);
    }
}