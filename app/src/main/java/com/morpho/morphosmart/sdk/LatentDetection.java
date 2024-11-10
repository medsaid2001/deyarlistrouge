package com.morpho.morphosmart.sdk;

public enum LatentDetection {
    LATENT_DETECT_DISABLE(0),
    LATENT_DETECT_ENABLE(1);
    
    private int value;

    private LatentDetection(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
