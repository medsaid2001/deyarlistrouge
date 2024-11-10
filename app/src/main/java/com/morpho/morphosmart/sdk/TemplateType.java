package com.morpho.morphosmart.sdk;


import com.facebook.shimmer.BuildConfig;

public enum TemplateType implements ITemplateType {
    MORPHO_PK_COMP(0, "SAGEM PkComp", ".pkc"),
    MORPHO_PK_MAT_NORM(1, "SAGEM PkMat Norm", ".pkmn"),
    MORPHO_PK_COMP_NORM(2, "SAGEM PkComp Norm", ".pkcn"),
    MORPHO_PK_MAT(3, "SAGEM PkMat", ".pkm"),
    MORPHO_PK_ANSI_378(4, "ANSI INCITS 378", ".ansi-fmr"),
    MORPHO_PK_MINEX_A(5, "MINEX A", ".minex-a"),
    MORPHO_PK_ISO_FMR(6, "ISO 19794-2", ".iso-fmr"),
    MORPHO_PK_ISO_FMC_NS(7, "ISO 19794-2, FMC Normal Size", ".iso-fmc-ns"),
    MORPHO_PK_ISO_FMC_CS(8, "ISO 19794-2, FMC Compact Size", ".iso-fmc-cs"),
    MORPHO_PK_ILO_FMR(9, "ILO International Labour Organisation", ".ilo-fmr"),
    MORPHO_PK_MOC(12, "SAGEM PKMOC", ".moc"),
    MORPHO_PK_DIN_V66400_CS(13, "DIN V66400 Compact Size", ".din-cs"),
    MORPHO_PK_DIN_V66400_CS_AA(14, "DIN V66400 Compact Size, ordered by Ascending Angle", ".din-cs"),
    MORPHO_PK_ISO_FMC_CS_AA(15, "ISO 19794-2, FMC Compact Size, ordered by Ascending Angle", ".iso-fmc-cs"),
    MORPHO_PK_CFV(16, "Morpho proprietary CFV Fingerprint Template", ".cfv"),
    MORPHO_PK_BIOSCRYPT(17, "Bioscrypt Fingerprint Template", ".bioscrypt"),
    MORPHO_NO_PK_FP(18, "NO PK FP", BuildConfig.FLAVOR),
    MORPHO_PK_ANSI_378_2009(19, "ANSI INCITS 378 standard Version 2009", ".ansi-fmr-2009"),
    MORPHO_PK_ISO_FMR_2011(20, "ISO/IEC 19794-2 standard Version 2011", ".iso-fmr-2011"),
    MORPHO_PK_PKLITE(21, "pk lite Fingerprint Template", ".pklite");
    
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

    private TemplateType(int i, String str, String str2) {
        this.code = i;
        this.label = str;
        this.extension = str2;
    }

    public static TemplateType getValue(int i) {
        TemplateType[] values = values();
        for (int i2 = 0; i2 < values.length; i2++) {
            if (values[i2].code == i) {
                return values[i2];
            }
        }
        return MORPHO_NO_PK_FP;
    }
}
