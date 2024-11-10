package com.morpho.morphosmart.sdk;

public enum EnrollmentType {
    ONE_ACQUISITIONS(1),
    THREE_ACQUISITIONS(0);
    
    private int value;

    private EnrollmentType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
