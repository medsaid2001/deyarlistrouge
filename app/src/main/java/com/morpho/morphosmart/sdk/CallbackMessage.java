package com.morpho.morphosmart.sdk;

public final class CallbackMessage {
    private Object message;
    private int messageType;

    public int getMessageType() {
        return this.messageType;
    }

    public void setMessageType(int i) {
        this.messageType = i;
    }

    public Object getMessage() {
        return this.message;
    }

    public void setMessage(Object obj) {
        this.message = obj;
    }
}
