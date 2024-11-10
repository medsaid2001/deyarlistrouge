package com.morpho.morphosmart.sdk;

import android.annotation.SuppressLint;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.internal.Conversions;
import org.aspectj.runtime.reflect.Factory;

import java.util.Observer;

import com.facebook.shimmer.BuildConfig;
import com.morpho.android.annotation.WakeLockAspect;
import com.morpho.android.annotation.WakeLockabble;


@SuppressLint({"UseValueOf"})
public class MorphoUser implements Cloneable {
    private static /* synthetic */ JoinPoint.StaticPart ajc$tjp_0 = null;
    private static /* synthetic */ JoinPoint.StaticPart ajc$tjp_1 = null;
    private static /* synthetic */ JoinPoint.StaticPart ajc$tjp_2 = null;
    private static /* synthetic */ JoinPoint.StaticPart ajc$tjp_3 = null;
    private static /* synthetic */ JoinPoint.StaticPart ajc$tjp_4 = null;
    private static /* synthetic */ JoinPoint.StaticPart ajc$tjp_5 = null;
    private static /* synthetic */ JoinPoint.StaticPart ajc$tjp_6 = null;
    private static MorphoUserNative morphoUserNative = new MorphoUserNative();
    protected boolean cppMemOwn = false;
    private Long morphoUserPointerCPP = new Long(0);
    private String userID;

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("MorphoUser.java", MorphoUser.class);
        ajc$tjp_0 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "dbDelete", "com.morpho.morphosmart.sdk.MorphoUser", BuildConfig.FLAVOR, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "int"), 207);
        ajc$tjp_1 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "dbStore", "com.morpho.morphosmart.sdk.MorphoUser", BuildConfig.FLAVOR, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "int"), 246);
        ajc$tjp_2 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "enroll", "com.morpho.morphosmart.sdk.MorphoUser", "int:int:int:com.morpho.morphosmart.sdk.CompressionAlgorithm:int:boolean:int:com.morpho.morphosmart.sdk.TemplateType:com.morpho.morphosmart.sdk.TemplateFVPType:boolean:com.morpho.morphosmart.sdk.Coder:int:com.morpho.morphosmart.sdk.TemplateList:int:java.util.Observer", "timeout:acquisitionThreshold:advancedSecurityLevelsRequired:compressAlgo:compressRate:exportMinutiae:fingerNumber:templateType:templateFVPType:saveRecord:coder:detectModeChoice:templateList:callbackCmd:callback", BuildConfig.FLAVOR, "int"), 380);
        ajc$tjp_3 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "enroll", "com.morpho.morphosmart.sdk.MorphoUser", "int:int:int:com.morpho.morphosmart.sdk.CompressionAlgorithm:int:int:int:com.morpho.morphosmart.sdk.TemplateType:com.morpho.morphosmart.sdk.TemplateFVPType:boolean:com.morpho.morphosmart.sdk.Coder:int:com.morpho.morphosmart.sdk.TemplateList:int:java.util.Observer", "timeout:acquisitionThreshold:advancedSecurityLevelsRequired:compressAlgo:compressRate:exportMinutiae:fingerNumber:templateType:templateFVPType:saveRecord:coder:detectModeChoice:templateList:callbackCmd:callback", BuildConfig.FLAVOR, "int"), 512);
        ajc$tjp_4 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "verify", "com.morpho.morphosmart.sdk.MorphoUser", "int:int:com.morpho.morphosmart.sdk.Coder:int:com.morpho.morphosmart.sdk.MatchingStrategy:int:java.util.Observer:com.morpho.morphosmart.sdk.ResultMatching", "timeout:far:coder:detectModeChoice:matchingStrategy:callbackCmd:callback:resultMatching", BuildConfig.FLAVOR, "int"), 573);
        ajc$tjp_5 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "dbUpdatePublicFields", "com.morpho.morphosmart.sdk.MorphoUser", BuildConfig.FLAVOR, BuildConfig.FLAVOR, BuildConfig.FLAVOR, "int"), 638);
        ajc$tjp_6 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "dbVerifyAndUpdate", "com.morpho.morphosmart.sdk.MorphoUser", "int:int:com.morpho.morphosmart.sdk.Coder:int:com.morpho.morphosmart.sdk.MatchingStrategy:int:java.util.Observer", "timeout:far:coder:detectModeChoice:matchingStrategy:callbackCmd:callback", BuildConfig.FLAVOR, "int"), 686);
    }

    @WakeLockabble
    public int dbDelete() {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_0, this, this);
        return Conversions.intValue(dbDelete_aroundBody1$advice(this, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int dbStore() {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_1, this, this);
        return Conversions.intValue(dbStore_aroundBody3$advice(this, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int dbUpdatePublicFields() {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_5, this, this);
        return Conversions.intValue(dbUpdatePublicFields_aroundBody11$advice(this, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int dbVerifyAndUpdate(int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer) {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_6, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), coder, Conversions.intObject(i3), matchingStrategy, Conversions.intObject(i4), observer});
        return Conversions.intValue(dbVerifyAndUpdate_aroundBody13$advice(this, i, i2, coder, i3, matchingStrategy, i4, observer, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int enroll(int i, int i2, int i3, CompressionAlgorithm compressionAlgorithm, int i4, int i5, int i6, TemplateType templateType, TemplateFVPType templateFVPType, boolean z, Coder coder, int i7, TemplateList templateList, int i8, Observer observer) {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_3, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), Conversions.intObject(i3), compressionAlgorithm, Conversions.intObject(i4), Conversions.intObject(i5), Conversions.intObject(i6), templateType, templateFVPType, Conversions.booleanObject(z), coder, Conversions.intObject(i7), templateList, Conversions.intObject(i8), observer});
        return Conversions.intValue(enroll_aroundBody7$advice(this, i, i2, i3, compressionAlgorithm, i4, i5, i6, templateType, templateFVPType, z, coder, i7, templateList, i8, observer, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int enroll(int i, int i2, int i3, CompressionAlgorithm compressionAlgorithm, int i4, boolean z, int i5, TemplateType templateType, TemplateFVPType templateFVPType, boolean z2, Coder coder, int i6, TemplateList templateList, int i7, Observer observer) {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_2, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), Conversions.intObject(i3), compressionAlgorithm, Conversions.intObject(i4), Conversions.booleanObject(z), Conversions.intObject(i5), templateType, templateFVPType, Conversions.booleanObject(z2), coder, Conversions.intObject(i6), templateList, Conversions.intObject(i7), observer});
        return Conversions.intValue(enroll_aroundBody5$advice(this, i, i2, i3, compressionAlgorithm, i4, z, i5, templateType, templateFVPType, z2, coder, i6, templateList, i7, observer, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int verify(int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, ResultMatching resultMatching) {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_4, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), coder, Conversions.intObject(i3), matchingStrategy, Conversions.intObject(i4), observer, resultMatching});
        return Conversions.intValue(verify_aroundBody9$advice(this, i, i2, coder, i3, matchingStrategy, i4, observer, resultMatching, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    static {
        ajc$preClinit();
    }

    public MorphoUser() {
        long cPPInstance = morphoUserNative.getCPPInstance();
        if (cPPInstance != 0) {
            this.cppMemOwn = true;
            this.morphoUserPointerCPP = Long.valueOf(cPPInstance);
            return;
        }
        try {
            throw new MorphoSmartException("classe non instantiÃƒÂ©");
        } catch (MorphoSmartException e) {
            e.printStackTrace();
        }
    }

    public MorphoUser(String str) {
        if (str != null) {
            long cPPInstance = morphoUserNative.getCPPInstance(str);
            if (cPPInstance != 0) {
                this.cppMemOwn = true;
                this.morphoUserPointerCPP = Long.valueOf(cPPInstance);
                this.userID = str;
                return;
            }
            try {
                throw new MorphoSmartException("cppPtr is null");
            } catch (MorphoSmartException e) {
                e.printStackTrace();
            }
        } else {
            try {
                throw new MorphoSmartException("USer ID object is null");
            } catch (MorphoSmartException e2) {
                e2.printStackTrace();
            }
        }
    }

    private static final /* synthetic */ Object dbDelete_aroundBody1$advice(MorphoUser morphoUser, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(dbDelete_aroundBody0(morphoUser, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static final /* synthetic */ Object dbStore_aroundBody3$advice(MorphoUser morphoUser, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(dbStore_aroundBody2(morphoUser, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static final /* synthetic */ Object dbUpdatePublicFields_aroundBody11$advice(MorphoUser morphoUser, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(dbUpdatePublicFields_aroundBody10(morphoUser, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static final /* synthetic */ Object dbVerifyAndUpdate_aroundBody13$advice(MorphoUser morphoUser, int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(dbVerifyAndUpdate_aroundBody12(morphoUser, i, i2, coder, i3, matchingStrategy, i4, observer, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static final /* synthetic */ Object enroll_aroundBody5$advice(MorphoUser morphoUser, int i, int i2, int i3, CompressionAlgorithm compressionAlgorithm, int i4, boolean z, int i5, TemplateType templateType, TemplateFVPType templateFVPType, boolean z2, Coder coder, int i6, TemplateList templateList, int i7, Observer observer, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(enroll_aroundBody4(morphoUser, i, i2, i3, compressionAlgorithm, i4, z, i5, templateType, templateFVPType, z2, coder, i6, templateList, i7, observer, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static final /* synthetic */ Object enroll_aroundBody7$advice(MorphoUser morphoUser, int i, int i2, int i3, CompressionAlgorithm compressionAlgorithm, int i4, int i5, int i6, TemplateType templateType, TemplateFVPType templateFVPType, boolean z, Coder coder, int i7, TemplateList templateList, int i8, Observer observer, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(enroll_aroundBody6(morphoUser, i, i2, i3, compressionAlgorithm, i4, i5, i6, templateType, templateFVPType, z, coder, i7, templateList, i8, observer, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static final /* synthetic */ Object verify_aroundBody9$advice(MorphoUser morphoUser, int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, ResultMatching resultMatching, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajc$inlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(verify_aroundBody8(morphoUser, i, i2, coder, i3, matchingStrategy, i4, observer, resultMatching, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    public MorphoUser(MorphoUser morphoUser) {
        if (morphoUser == null) {
            try {
                throw new MorphoSmartException("MorphoUser object is null");
            } catch (MorphoSmartException e) {
                e.printStackTrace();
            }
        } else if (!morphoUser.cppMemOwn) {
            long cPPInstance = morphoUserNative.getCPPInstance(morphoUser.morphoUserPointerCPP.longValue());
            if (cPPInstance != 0) {
                this.cppMemOwn = true;
                this.morphoUserPointerCPP = Long.valueOf(cPPInstance);
                return;
            }
            try {
                throw new MorphoSmartException("cppPtr is null");
            } catch (MorphoSmartException e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                throw new MorphoSmartException("cppMemOwn is true");
            } catch (MorphoSmartException e3) {
                e3.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (this.cppMemOwn) {
            morphoUserNative.deleteInstance(this.morphoUserPointerCPP.longValue());
            this.cppMemOwn = false;
        }
    }

    public Object clone() {
        return new MorphoUser(this);
    }

    public void setMorphoUserPointerCPP(long j) {
        this.morphoUserPointerCPP = Long.valueOf(j);
        this.cppMemOwn = true;
    }

    public String getUserID() {
        return this.userID;
    }

    public long getMorphoUserPointerCPP() {
        return this.morphoUserPointerCPP.longValue();
    }

    public int cancelLiveAcquisition() {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoUserNative.cancelLiveAcquisition(this.morphoUserPointerCPP.longValue());
    }

    private static final /* synthetic */ int dbDelete_aroundBody0(MorphoUser morphoUser, JoinPoint joinPoint) {
        if (!morphoUser.cppMemOwn) {
            return -98;
        }
        return morphoUserNative.dbDelete(morphoUser.morphoUserPointerCPP.longValue());
    }

    private static final /* synthetic */ int dbStore_aroundBody2(MorphoUser morphoUser, JoinPoint joinPoint) {
        if (!morphoUser.cppMemOwn) {
            return -98;
        }
        return morphoUserNative.dbStore(morphoUser.morphoUserPointerCPP.longValue());
    }

    public int setNoCheckOnTemplateForDBStore(boolean z) {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoUserNative.setNoCheckOnTemplateForDBStore(this.morphoUserPointerCPP.longValue(), z);
    }

    public int setMaskCheckOnTemplateForDBStore(int i) {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoUserNative.setMaskCheckOnTemplateForDBStore(this.morphoUserPointerCPP.longValue(), i);
    }

    public int putField(int i, String str) {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoUserNative.putField(this.morphoUserPointerCPP.longValue(), i, str);
    }

    public int putBufferField(int i, byte[] bArr) {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoUserNative.putBufferField(this.morphoUserPointerCPP.longValue(), i, bArr);
    }

    public String getField(int i) throws MorphoSmartException {
        MorphoString morphoString = new MorphoString();
        int field = morphoUserNative.getField(this.morphoUserPointerCPP.longValue(), i, morphoString);
        if (field == 0) {
            return morphoString.getData();
        }
        throw new MorphoSmartException(ErrorCodes.getError(field, 0));
    }

    public byte[] getBufferField(int i) throws MorphoSmartException {
        MorphoString morphoString = new MorphoString();
        int bufferField = morphoUserNative.getBufferField(this.morphoUserPointerCPP.longValue(), i, morphoString);
        if (bufferField == 0) {
            return morphoString.getBufferData();
        }
        throw new MorphoSmartException(ErrorCodes.getError(bufferField, 0));
    }

    private static final /* synthetic */ int enroll_aroundBody4(MorphoUser morphoUser, int i, int i2, int i3, CompressionAlgorithm compressionAlgorithm, int i4, boolean z, int i5, TemplateType templateType, TemplateFVPType templateFVPType, boolean z2, Coder coder, int i6, TemplateList templateList, int i7, Observer observer, JoinPoint joinPoint) {
        MorphoUser morphoUser2 = morphoUser;
        if (!morphoUser2.cppMemOwn) {
            return -98;
        }
        if (compressionAlgorithm == null || templateType == null || templateFVPType == null || coder == null || templateList == null) {
            return -5;
        }
        int i8 = i5;
        boolean z3 = z2;
        return morphoUserNative.enroll(morphoUser2.morphoUserPointerCPP.longValue(), i, i2, i3, compressionAlgorithm.getCode(), i4, z ? 1 : 0, i8, templateType.getCode(), templateFVPType.getCode(), z3, coder.getCode(), i6, templateList, i7, observer);
    }

    private static final /* synthetic */ int enroll_aroundBody6(MorphoUser morphoUser, int i, int i2, int i3, CompressionAlgorithm compressionAlgorithm, int i4, int i5, int i6, TemplateType templateType, TemplateFVPType templateFVPType, boolean z, Coder coder, int i7, TemplateList templateList, int i8, Observer observer, JoinPoint joinPoint) {
        MorphoUser morphoUser2 = morphoUser;
        if (!morphoUser2.cppMemOwn) {
            return -98;
        }
        if (compressionAlgorithm == null || templateType == null || templateFVPType == null || coder == null || templateList == null) {
            return -5;
        }
        return morphoUserNative.enroll(morphoUser2.morphoUserPointerCPP.longValue(), i, i2, i3, compressionAlgorithm.getCode(), i4, i5, i6, templateType.getCode(), templateFVPType.getCode(), z, coder.getCode(), i7, templateList, i8, observer);
    }

    private static final /* synthetic */ int verify_aroundBody8(MorphoUser morphoUser, int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, ResultMatching resultMatching, JoinPoint joinPoint) {
        MorphoUser morphoUser2 = morphoUser;
        if (!morphoUser2.cppMemOwn) {
            return -98;
        }
        if (coder == null || matchingStrategy == null) {
            return -5;
        }
        MorphoUserNative morphoUserNative2 = morphoUserNative;
        long longValue = morphoUser2.morphoUserPointerCPP.longValue();
        return morphoUserNative2.verify(longValue, i, i2, coder.getCode(), i3, matchingStrategy.getValue(), i4, observer, resultMatching);
    }

    public int putTemplate(Template template, Integer num) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (template == null || num == null) {
            return -5;
        }
        return morphoUserNative.putTemplate(this.morphoUserPointerCPP.longValue(), template, num);
    }

    private static final /* synthetic */ int dbUpdatePublicFields_aroundBody10(MorphoUser morphoUser, JoinPoint joinPoint) {
        if (!morphoUser.cppMemOwn) {
            return -98;
        }
        return morphoUserNative.dbUpdatePublicFields(morphoUser.morphoUserPointerCPP.longValue());
    }

    private static final /* synthetic */ int dbVerifyAndUpdate_aroundBody12(MorphoUser morphoUser, int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, JoinPoint joinPoint) {
        MorphoUser morphoUser2 = morphoUser;
        if (!morphoUser2.cppMemOwn) {
            return -98;
        }
        if (coder == null || matchingStrategy == null) {
            return -5;
        }
        MorphoUserNative morphoUserNative2 = morphoUserNative;
        long longValue = morphoUser2.morphoUserPointerCPP.longValue();
        return morphoUserNative2.dbVerifyAndUpdate(longValue, i, i2, coder.getCode(), i3, matchingStrategy.getValue(), i4, observer);
    }

    public int getEnrollmentType() {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoUserNative.getEnrollmentType(this.morphoUserPointerCPP.longValue());
    }

    public int setEnrollmentType(EnrollmentType enrollmentType) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (enrollmentType == null) {
            return -5;
        }
        return morphoUserNative.setEnrollmentType(this.morphoUserPointerCPP.longValue(), enrollmentType.getValue());
    }

    public int getUserTemplateQuality(int i, String str, int i2, long j, TemplateQuality templateQuality, FingerNumber fingerNumber) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (str == null || templateQuality == null || fingerNumber == null) {
            return -5;
        }
        return morphoUserNative.getUserTemplateQuality(this.morphoUserPointerCPP.longValue(), i, str, i2, j, templateQuality, fingerNumber);
    }

    public int putFVPTemplate(TemplateFVP templateFVP, CustomInteger customInteger) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (templateFVP == null || customInteger == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoUserNative.putFVPTemplate(this.morphoUserPointerCPP.longValue(), templateFVP, customInteger);
    }

    public int getTemplate(int i, Template template) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (template == null) {
            return -5;
        }
        return morphoUserNative.getTemplate(this.morphoUserPointerCPP.longValue(), i, template);
    }

    public int getFVPTemplate(int i, TemplateFVP templateFVP) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (templateFVP == null) {
            return -5;
        }
        return morphoUserNative.getFVPTemplate(this.morphoUserPointerCPP.longValue(), i, templateFVP);
    }

    public int getNbTemplate(CustomInteger customInteger) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customInteger == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoUserNative.getNbTemplate(this.morphoUserPointerCPP.longValue(), customInteger);
    }

    public int getNbTemplateFVP(CustomInteger customInteger) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customInteger == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoUserNative.getNbTemplateFVP(this.morphoUserPointerCPP.longValue(), customInteger);
    }

    public int setTemplateUpdateMask(boolean[] zArr) {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoUserNative.setTemplateUpdateMask(this.morphoUserPointerCPP.longValue(), zArr);
    }
}
