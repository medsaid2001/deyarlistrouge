package com.morpho.morphosmart.sdk;

public enum DetectionMode {
    MORPHO_DEFAULT_DETECT_MODE(0),
    MORPHO_VERIF_DETECT_MODE(1),
    MORPHO_ENROLL_DETECT_MODE(2),
    MORPHO_WAKEUP_LED_OFF(4),
    MORPHO_FORCE_FINGER_ON_TOP_DETECT_MODE(16),
    MORPHO_WAKEUP_LED_ON(64);
    
    private int value;

    private DetectionMode(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
