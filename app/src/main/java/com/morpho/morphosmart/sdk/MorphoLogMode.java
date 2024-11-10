package com.morpho.morphosmart.sdk;

public enum MorphoLogMode {
    MORPHO_LOG_ENABLE(0, "Enable"),
    MORPHO_LOG_DISABLE(1, "Disable");
    
    private int code;
    private String label;

    public int getCode() {
        return this.code;
    }

    private MorphoLogMode(int i, String str) {
        this.code = i;
        this.label = str;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public static MorphoLogMode fromString(String str) {
        if (str == null) {
            return null;
        }
        for (MorphoLogMode morphoLogMode : values()) {
            if (str.equalsIgnoreCase(morphoLogMode.getLabel())) {
                return morphoLogMode;
            }
        }
        return null;
    }
}
