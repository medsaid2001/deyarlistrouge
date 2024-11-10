package com.morpho.morphosmart.sdk;

public enum MorphoKCVID {
    ID_KENC(0),
    ID_KSECRET(1),
    ID_KS(2),
    ID_KPRIVACY(3);
    
    private int code;

    public int getCode() {
        return this.code;
    }

    private MorphoKCVID(int i) {
        this.code = i;
    }
}
