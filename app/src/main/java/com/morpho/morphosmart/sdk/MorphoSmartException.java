package com.morpho.morphosmart.sdk;

public class MorphoSmartException extends RuntimeException {
    private static String error_msg = "";
    private static final long serialVersionUID = 1;

    public MorphoSmartException() {
    }

    public MorphoSmartException(String str) {
        super(str);
    }

    public MorphoSmartException(Throwable th) {
        super(th);
    }

    public MorphoSmartException(String str, Throwable th) {
        super(str, th);
    }

    public MorphoSmartException(int i, String str, Throwable th) {
        super(str, th);
        error_msg = ErrorCodes.getError(i, 0);
    }

    public String getErrorMessage() {
        return error_msg;
    }
}
