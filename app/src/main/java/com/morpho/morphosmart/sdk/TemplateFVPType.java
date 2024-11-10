package com.morpho.morphosmart.sdk;

import com.facebook.shimmer.BuildConfig;

public enum TemplateFVPType implements ITemplateType {
    MORPHO_NO_PK_FVP(0, "NO PK FVP", BuildConfig.FLAVOR),
    MORPHO_PK_FVP(1, "SAGEM PkFVP", ".fvp"),
    MORPHO_PK_FVP_MATCH(2, "SAGEM PkFVP Match", ".fvp-m");
    
    private int code;
    private String extension;
    private String label;

    public int getCode() {
        return this.code;
    }

    public String getLabel() {
        return this.label;
    }

    public String getExtension() {
        return this.extension;
    }

    private TemplateFVPType(int i, String str, String str2) {
        this.code = i;
        this.label = str;
        this.extension = str2;
    }

    protected static TemplateFVPType getValue(int i) {
        TemplateFVPType[] values = values();
        for (int i2 = 0; i2 < values.length; i2++) {
            if (values[i2].code == i) {
                return values[i2];
            }
        }
        return MORPHO_NO_PK_FVP;
    }
}
