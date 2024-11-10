package com.morpho.morphosmart.sdk;

public enum MorphoWakeUpMode {
    MORPHO_WAKEUP_LED_ON(0, "Default"),
    MORPHO_WAKEUP_LED_OFF(4, "Check Wake Up Mode");
    
    private int code;
    private String label;

    public int getCode() {
        return this.code;
    }

    public String getLabel() {
        return this.label;
    }

    private MorphoWakeUpMode(int i, String str) {
        this.code = i;
        this.label = str;
    }
}
