package com.morpho.morphosmart.sdk;

public enum MorphoLogLevel {
    MORPHO_LOG_NOLOG(0, "No Log"),
    MORPHO_LOG_ERROR(1, "Error"),
    MORPHO_LOG_WARNING(2, "Warning"),
    MORPHO_LOG_INFO(3, "Info"),
    MORPHO_LOG_DEBUG(4, "Debug");
    
    private int code;
    private String label;

    public int getCode() {
        return this.code;
    }

    private MorphoLogLevel(int i, String str) {
        this.code = i;
        this.label = str;
    }

    public String getLabel() {
        return this.label;
    }

    public static MorphoLogLevel fromString(String str) {
        if (str == null) {
            return null;
        }
        for (MorphoLogLevel morphoLogLevel : values()) {
            if (str.equalsIgnoreCase(morphoLogLevel.getLabel())) {
                return morphoLogLevel;
            }
        }
        return null;
    }
}
