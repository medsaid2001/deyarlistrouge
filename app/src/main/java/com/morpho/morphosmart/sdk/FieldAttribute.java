package com.morpho.morphosmart.sdk;

public enum FieldAttribute {
    MORPHO_PUBLIC_FIELD(0),
    MORPHO_PRIVATE_FIELD(1);
    
    private int value;

    private FieldAttribute(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
