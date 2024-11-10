package com.morpho.morphosmart.sdk;


import static com.facebook.shimmer.BuildConfig.FLAVOR;

public enum CompressionAlgorithm {
    NO_IMAGE(-1, "NO IMAGE", FLAVOR),
    MORPHO_NO_COMPRESS(0, "RAW", ".raw"),
    MORPHO_COMPRESS_V1(1, "SAGEM_V1", ".bin"),
    MORPHO_COMPRESS_WSQ(2, "WSQ", ".wsq");
    
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

    private CompressionAlgorithm(int i, String str, String str2) {
        this.code = i;
        this.label = str;
        this.extension = str2;
    }

    public static CompressionAlgorithm GetCompressionAlgorithm(int i) {
        if (i == 44) {
            return MORPHO_NO_COMPRESS;
        }
        if (i == 60) {
            return MORPHO_COMPRESS_V1;
        }
        if (i != 156) {
            return MORPHO_NO_COMPRESS;
        }
        return MORPHO_COMPRESS_WSQ;
    }

    protected static CompressionAlgorithm getValue(int i) {
        CompressionAlgorithm[] values = values();
        for (int i2 = 0; i2 < values.length; i2++) {
            if (values[i2].code == i) {
                return values[i2];
            }
        }
        return MORPHO_NO_COMPRESS;
    }
}
