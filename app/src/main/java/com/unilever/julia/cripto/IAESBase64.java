package com.unilever.julia.cripto;

public interface IAESBase64 {

    String encodeBase64String(final byte[] binaryData);

    byte[] decodeBase64(final String base64String);
}
