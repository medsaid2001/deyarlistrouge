package com.morpho.morphosmart.sdk;

public class MorphoString {
    private byte[] bufferData = null;
    private String data = new String();

    public String getData() {
        return this.data;
    }

    public byte[] getBufferData() {
        return this.bufferData;
    }

    public void setData(String str) {
        this.data = str;
    }

    public void setBufferData(byte[] bArr) {
        this.bufferData = bArr;
    }
}
