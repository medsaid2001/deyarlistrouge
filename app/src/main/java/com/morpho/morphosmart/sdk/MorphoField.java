package com.morpho.morphosmart.sdk;

public class MorphoField {
    public static final int MORPHO_FIELD_NAME_LEN = 6;
    private FieldAttribute fieldAttribute;
    private int maxSize;
    private String name;

    public FieldAttribute getFieldAttribute() {
        return this.fieldAttribute;
    }

    public int getFieldAttributeIntValue() {
        return this.fieldAttribute.ordinal();
    }

    public void setFieldAttribute(FieldAttribute fieldAttribute2) {
        this.fieldAttribute = fieldAttribute2;
    }

    public void setFieldAttributeIntValue(int i) {
        if (i == 0) {
            this.fieldAttribute = FieldAttribute.MORPHO_PUBLIC_FIELD;
        } else if (i == 1) {
            this.fieldAttribute = FieldAttribute.MORPHO_PRIVATE_FIELD;
        }
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int i) {
        this.maxSize = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) throws MorphoSmartException {
        if (str == null) {
            throw new MorphoSmartException("Invalid parameter");
        } else if (str.length() <= 6) {
            this.name = str;
        } else {
            throw new MorphoSmartException("Invalid field size, field size must be less than equal to 6");
        }
    }
}
