package com.morpho.morphosmart.sdk;

import java.util.Observer;

/* compiled from: MorphoUser */
class MorphoUserNative {
    public native int cancelLiveAcquisition(long j);

    public native int dbDelete(long j);

    public native int dbStore(long j);

    public native int dbUpdatePublicFields(long j);

    public native int dbVerifyAndUpdate(long j, int i, int i2, int i3, int i4, int i5, int i6, Observer observer);

    public native void deleteInstance(long j);

    public native int enroll(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z, int i10, int i11, TemplateList templateList, int i12, Observer observer);

    public native int getBufferField(long j, int i, MorphoString morphoString);

    public native long getCPPInstance();

    public native long getCPPInstance(long j);

    public native long getCPPInstance(String str);

    public native int getEnrollmentType(long j);

    public native int getFVPTemplate(long j, int i, TemplateFVP templateFVP);

    public native int getField(long j, int i, MorphoString morphoString);

    public native int getNbTemplate(long j, CustomInteger customInteger);

    public native int getNbTemplateFVP(long j, CustomInteger customInteger);

    public native int getTemplate(long j, int i, Template template);

    public native int getUserTemplateQuality(long j, int i, String str, int i2, long j2, TemplateQuality templateQuality, FingerNumber fingerNumber);

    public native int getUserTemplateQuality(long j, int i, String str, int i2, Integer num, Integer num2);

    public native int putBufferField(long j, int i, byte[] bArr);

    public native int putFVPTemplate(long j, TemplateFVP templateFVP, CustomInteger customInteger);

    public native int putField(long j, int i, String str);

    public native int putTemplate(long j, Template template, Integer num);

    public native int setEnrollmentType(long j, int i);

    public native int setMaskCheckOnTemplateForDBStore(long j, int i);

    public native int setNoCheckOnTemplateForDBStore(long j, boolean z);

    public native int setTemplateUpdateMask(long j, boolean[] zArr);

    public native int verify(long j, int i, int i2, int i3, int i4, int i5, int i6, Observer observer, ResultMatching resultMatching);

    MorphoUserNative() {
    }

    static {
        System.loadLibrary("NativeMorphoSmartSDK_6.36.1.0");
        System.loadLibrary("MSO100");
    }
}
