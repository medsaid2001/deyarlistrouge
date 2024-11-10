package com.morpho.morphosmart.sdk;

public enum MorphoDeviceConnectionType {
    COM_TYPE_NOT_SET(0),
    RS232_COM_TYPE(1),
    USB_COM_TYPE(2),
    USER_COM_TYPE(3);
    
    private int code;

    public int getCode() {
        return this.code;
    }

    private MorphoDeviceConnectionType(int i) {
        this.code = i;
    }
}
