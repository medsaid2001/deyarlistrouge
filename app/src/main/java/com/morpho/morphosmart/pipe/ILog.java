package com.morpho.morphosmart.pipe;

public interface ILog {
    void disableLog();

    void enableLog();

    boolean isEnabled();

    void log(String str);
}
