package com.morpho.morphosmart.sdk;

public enum MorphoTypeDeletion {
    MORPHO_ERASE_BASE(0),
    MORPHO_DESTROY_BASE(1);
    
    private int value;

    private MorphoTypeDeletion(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
