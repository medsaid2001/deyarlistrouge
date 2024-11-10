package com.morpho.morphosmart.pipe;

public class SimpleLogger implements ILog {
    private boolean enableLog;

    public void log(String str) {
        if (isEnabled()) {
            System.out.print(str);
        }
    }

    public boolean isEnabled() {
        return this.enableLog;
    }

    public void enableLog() {
        this.enableLog = true;
    }

    public void disableLog() {
        this.enableLog = false;
    }
}
