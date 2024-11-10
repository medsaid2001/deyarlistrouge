package com.morpho.morphosmart.sdk;

public class FingerNumber {
    private byte[] fingerNumber = new byte[100];

    public byte[] getFingerNumber() {
        return this.fingerNumber;
    }

    public void setFingerNumber(byte[] bArr) {
        this.fingerNumber = bArr;
    }
}
