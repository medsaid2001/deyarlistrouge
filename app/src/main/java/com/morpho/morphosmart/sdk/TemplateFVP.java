package com.morpho.morphosmart.sdk;

public class TemplateFVP {
    private boolean advancedSecurityLevelsCompatibility;
    private byte[] data;
    private int dataIndex = 255;
    private TemplateFVPType templateFVPType;
    private int templateQuality;

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    private void setData(Object obj) {
        this.data = (byte[]) obj;
    }

    public int getDataIndex() {
        return this.dataIndex;
    }

    public void setDataIndex(int i) {
        this.dataIndex = i;
    }

    public int getTemplateQuality() {
        return this.templateQuality;
    }

    public void setTemplateQuality(int i) {
        this.templateQuality = i;
    }

    public boolean getAdvancedSecurityLevelsCompatibility() {
        return this.advancedSecurityLevelsCompatibility;
    }

    public void setAdvancedSecurityLevelsCompatibility(boolean z) {
        this.advancedSecurityLevelsCompatibility = z;
    }

    public TemplateFVPType getTemplateFVPType() {
        return this.templateFVPType;
    }

    public void setTemplateFVPType(TemplateFVPType templateFVPType2) {
        this.templateFVPType = templateFVPType2;
    }

    private int getTemplateFVPTypeIntValue() {
        TemplateFVPType templateFVPType2 = this.templateFVPType;
        if (templateFVPType2 != null) {
            return templateFVPType2.getCode();
        }
        return -1;
    }

    public void setTemplateFVPType(int i) {
        this.templateFVPType = TemplateFVPType.getValue(i);
    }
}
