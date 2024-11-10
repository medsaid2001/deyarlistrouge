package com.morpho.morphosmart.sdk;


import androidx.core.internal.view.SupportMenu;

public enum SecurityLevel {
    FFD_SECURITY_LEVEL_LOW_HOST(0, "Low"),
    FFD_SECURITY_LEVEL_MEDIUM_HOST(1, "Medium"),
    FFD_SECURITY_LEVEL_HIGH_HOST(2, "High"),
    FFD_SECURITY_LEVEL_NONE_HOST(3, "None"),
    FFD_SECURITY_LEVEL_CRITICAL_HOST(4, "Critical"),
    MULTIMODAL_SECURITY_STANDARD(0, "Standard"),
    MULTIMODAL_SECURITY_MEDIUM(512, "Medium"),
    MULTIMODAL_SECURITY_HIGH(256, "High"),
    MULTIMODAL_SECURITY_CRITICAL(1024, "Critical"),
    FFD_SECURITY_LEVEL_DEFAULT_HOST(SupportMenu.USER_MASK, "Default");
    
    private String label;
    private int value;

    private SecurityLevel(int i, String str) {
        this.value = i;
        this.label = str;
    }

    public int getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }

    public static SecurityLevel fromInt(int i, boolean z) {
        if (z) {
            SecurityLevel securityLevel = MULTIMODAL_SECURITY_STANDARD;
            if (i == securityLevel.value) {
                return securityLevel;
            }
            SecurityLevel securityLevel2 = MULTIMODAL_SECURITY_MEDIUM;
            if (i == securityLevel2.value) {
                return securityLevel2;
            }
            SecurityLevel securityLevel3 = MULTIMODAL_SECURITY_HIGH;
            if (i == securityLevel3.value) {
                return securityLevel3;
            }
        } else {
            SecurityLevel securityLevel4 = FFD_SECURITY_LEVEL_LOW_HOST;
            if (i == securityLevel4.value) {
                return securityLevel4;
            }
            SecurityLevel securityLevel5 = FFD_SECURITY_LEVEL_MEDIUM_HOST;
            if (i == securityLevel5.value) {
                return securityLevel5;
            }
            SecurityLevel securityLevel6 = FFD_SECURITY_LEVEL_HIGH_HOST;
            if (i == securityLevel6.value) {
                return securityLevel6;
            }
            SecurityLevel securityLevel7 = FFD_SECURITY_LEVEL_CRITICAL_HOST;
            if (i == securityLevel7.value) {
                return securityLevel7;
            }
        }
        return FFD_SECURITY_LEVEL_DEFAULT_HOST;
    }

    public static SecurityLevel fromString(String str, boolean z) {
        if (str == null) {
            return null;
        }
        if (z) {
            if (str.equalsIgnoreCase(MULTIMODAL_SECURITY_STANDARD.label)) {
                return MULTIMODAL_SECURITY_STANDARD;
            }
            if (str.equalsIgnoreCase(MULTIMODAL_SECURITY_MEDIUM.label)) {
                return MULTIMODAL_SECURITY_MEDIUM;
            }
            if (str.equalsIgnoreCase(MULTIMODAL_SECURITY_HIGH.label)) {
                return MULTIMODAL_SECURITY_HIGH;
            }
            return null;
        } else if (str.equalsIgnoreCase(FFD_SECURITY_LEVEL_LOW_HOST.label)) {
            return FFD_SECURITY_LEVEL_LOW_HOST;
        } else {
            if (str.equalsIgnoreCase(FFD_SECURITY_LEVEL_MEDIUM_HOST.label)) {
                return FFD_SECURITY_LEVEL_MEDIUM_HOST;
            }
            if (str.equalsIgnoreCase(FFD_SECURITY_LEVEL_HIGH_HOST.label)) {
                return FFD_SECURITY_LEVEL_HIGH_HOST;
            }
            if (str.equalsIgnoreCase(FFD_SECURITY_LEVEL_CRITICAL_HOST.label)) {
                return FFD_SECURITY_LEVEL_CRITICAL_HOST;
            }
            if (str.equalsIgnoreCase(FFD_SECURITY_LEVEL_DEFAULT_HOST.label)) {
                return FFD_SECURITY_LEVEL_DEFAULT_HOST;
            }
            return null;
        }
    }
}
