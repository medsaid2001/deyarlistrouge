package com.morpho.morphosmart.sdk;

public class SecuConfig {
    private boolean acceptsOnlySignedTemplates = false;
    private boolean downloadIsProtected = false;
    private boolean exportScore = false;
    private int minMultimodalSecurityLevel;
    private boolean modeOfferedSecurity = false;
    private boolean modeTunneling = false;
    private int securityFAR;
    private char securityOptions;
    private String serialNumber;

    private int getIntOption(char c) {
        return c;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String str) {
        this.serialNumber = str;
    }

    public char getSecurityOptions() {
        return this.securityOptions;
    }

    public void setSecurityOptions(char c) {
        this.securityOptions = c;
        if ((c & 1) == 1) {
            setModeTunneling(true);
        }
        if ((c & 2) == 2) {
            setModeOfferedSecurity(true);
        }
        if ((c & 4) == 4) {
            setAcceptsOnlySignedTemplates(true);
        }
        if ((c & 8) == 8) {
            setDownloadIsProtected(true);
        }
        if ((c & 16) == 16) {
            setExportScore(true);
        }
    }

    public int getSecurityFAR() {
        return this.securityFAR;
    }

    public String getSecurityFARDescription() {
        switch (this.securityFAR) {
            case 0:
                return "All FAR are allowed";
            case 1:
                return "1 (1 %)";
            case 2:
                return "2 (0.3 %)";
            case 3:
                return "3 (0.1 %)";
            case 4:
                return "4 (0.03 %)";
            case 5:
                return "5 (0.01 %) : recommended";
            case 6:
                return "6 (0.001 %)";
            case 7:
                return "7 (0.0001 %)";
            case 8:
                return "8 (0.00001 %)";
            case 9:
                return "9 (0.0000001 %)";
            default:
                return "No FAR are allowed";
        }
    }

    public void setSecurityFAR(int i) {
        this.securityFAR = i;
    }

    public int getMinMultimodalSecurityLevel() {
        return this.minMultimodalSecurityLevel;
    }

    public void setMinMultimodalSecurityLevel(int i) {
        this.minMultimodalSecurityLevel = i;
    }

    public boolean isDownloadIsProtected() {
        return this.downloadIsProtected;
    }

    private void setDownloadIsProtected(boolean z) {
        this.downloadIsProtected = z;
    }

    public boolean isModeTunneling() {
        return this.modeTunneling;
    }

    private void setModeTunneling(boolean z) {
        this.modeTunneling = z;
    }

    public boolean isModeOfferedSecurity() {
        return this.modeOfferedSecurity;
    }

    private void setModeOfferedSecurity(boolean z) {
        this.modeOfferedSecurity = z;
    }

    public boolean isAcceptsOnlySignedTemplates() {
        return this.acceptsOnlySignedTemplates;
    }

    private void setAcceptsOnlySignedTemplates(boolean z) {
        this.acceptsOnlySignedTemplates = z;
    }

    public boolean isExportScore() {
        return this.exportScore;
    }

    private void setExportScore(boolean z) {
        this.exportScore = z;
    }
}
