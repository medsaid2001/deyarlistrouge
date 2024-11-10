package com.morpho.morphosmart.sdk;

public enum StrategyAcquisitionMode {
    MORPHO_ACQ_EXPERT_MODE(0, "Expert(Default)"),
    MORPHO_ACQ_UNIVERSAL_FAST_MODE(1, "Fast(Standard)"),
    MORPHO_ACQ_UNIVERSAL_ACCURATE_MODE(2, "Slow(Accurate)"),
    MORPHO_ACQ_FULL_MULTIMODAL_MODE(3, "Full MultiModal"),
    MORPHO_ACQ_ANTI_SPOOFING_MODE(4, "Anti Spoofing");
    
    private int code;
    private String label;

    public int getCode() {
        return this.code;
    }

    public String getLabel() {
        return this.label;
    }

    private StrategyAcquisitionMode(int i, String str) {
        this.code = i;
        this.label = str;
    }

    public static StrategyAcquisitionMode fromString(String str) {
        if (str == null) {
            return null;
        }
        for (StrategyAcquisitionMode strategyAcquisitionMode : values()) {
            if (str.equalsIgnoreCase(strategyAcquisitionMode.getLabel())) {
                return strategyAcquisitionMode;
            }
        }
        return null;
    }

    public static StrategyAcquisitionMode getValue(int i) {
        StrategyAcquisitionMode[] values = values();
        for (int i2 = 0; i2 < values.length; i2++) {
            if (values[i2].code == i) {
                return values[i2];
            }
        }
        return null;
    }
}
