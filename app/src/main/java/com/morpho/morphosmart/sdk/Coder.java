package com.morpho.morphosmart.sdk;

public enum Coder {
    MORPHO_DEFAULT_CODER(0, "Default"),
    MORPHO_MSO_V9_CODER(3, "Standard"),
    MORPHO_MSO_V9_JUV_CODER(7, "Juvenile"),
    MORPHO_MSO_V9_THIN_FINGER_CODER(8, "Thin Finger");
    
    private int code;
    private String label;

    public int getCode() {
        return this.code;
    }

    public String getLabel() {
        return this.label;
    }

    private Coder(int i, String str) {
        this.code = i;
        this.label = str;
    }

    public static Coder fromString(String str) {
        if (str == null) {
            return null;
        }
        for (Coder coder : values()) {
            if (str.equalsIgnoreCase(coder.getLabel())) {
                return coder;
            }
        }
        return null;
    }
}
