package com.morpho.morphosmart.sdk;

public class Template {
    private byte[] data;
    private int dataIndex = 255;
    private ITemplateType iTemplateType;
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

    public TemplateType getTemplateType() {
        return (TemplateType) this.iTemplateType;
    }

    public TemplateFVPType getTemplateFVPType() {
        return (TemplateFVPType) this.iTemplateType;
    }

    private int getTemplateTypeIntValue() {
        ITemplateType iTemplateType2 = this.iTemplateType;
        if (iTemplateType2 != null) {
            return ((TemplateType) iTemplateType2).getCode();
        }
        return -1;
    }

    public void setTemplateType(TemplateType templateType) {
        this.iTemplateType = templateType;
    }

    public void setTemplateFVPType(TemplateFVPType templateFVPType) {
        this.iTemplateType = templateFVPType;
    }

    public void setTemplateType(int i) {
        this.iTemplateType = TemplateType.getValue(i);
    }

    public void setTemplateFVPType(int i) {
        this.iTemplateType = TemplateFVPType.getValue(i);
    }
}
