package morpho.msosecu.sdk.api;

import com.morpho.morphosmart.sdk.IMsoSecu;
import java.util.ArrayList;

public class MsoSecu implements MsoSecuConstants, IMsoSecu {
    public int getHostCertif(ArrayList<Byte> arrayList) {
        return MsoSecuJNI.getHostCertif(arrayList);
    }

    public int genRandom(byte[] bArr) {
        return MsoSecuJNI.genRandom(bArr);
    }

    public int tunnelingInit1(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, ArrayList<Byte> arrayList) {
        return MsoSecuJNI.tunnelingInit1(bArr, bArr2, bArr3, bArr4, arrayList);
    }

    public int tunnelingInit2(byte[] bArr) {
        return MsoSecuJNI.tunnelingInit2(bArr);
    }

    public int tripleDesSign(byte[] bArr, ArrayList<Byte> arrayList) {
        return MsoSecuJNI.tripleDesSign(bArr, arrayList);
    }

    public int tripleDesCrypt(byte[] bArr, ArrayList<Byte> arrayList) {
        return MsoSecuJNI.tripleDesCrypt(bArr, arrayList);
    }

    public int desCrypt(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList) {
        return MsoSecuJNI.desCrypt(bArr, bArr2, arrayList);
    }

    public int tripleDesVerifSign(byte[] bArr, byte[] bArr2, boolean[] zArr) {
        return MsoSecuJNI.tripleDesVerifSign(bArr, bArr2, zArr);
    }

    public int tripleDesDecrypt(byte[] bArr, ArrayList<Byte> arrayList) {
        return MsoSecuJNI.tripleDesDecrypt(bArr, arrayList);
    }

    public int verifOfferedSecuritySignature(byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr) {
        return MsoSecuJNI.verifOfferedSecuritySignature(bArr, bArr2, bArr3, iArr);
    }

    public int verifSignRSA(byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr) {
        return MsoSecuJNI.verifSignRSA(bArr, bArr2, bArr3, iArr);
    }

    public int verifSignDSA(byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr) {
        return MsoSecuJNI.verifSignDSA(bArr, bArr2, bArr3, iArr);
    }

    public int verifCertif(byte[] bArr, int[] iArr) {
        return MsoSecuJNI.verifCertif(bArr, iArr);
    }

    public int tripleDesCryptByKey(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList) {
        return MsoSecuJNI.tripleDesCryptByKey(bArr, bArr2, arrayList);
    }

    public int rsaEncrypt(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList) {
        return MsoSecuJNI.rsaEncrypt(bArr, bArr2, arrayList);
    }

    public int signRSA(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList) {
        return MsoSecuJNI.signRSA(bArr, bArr2, arrayList);
    }

    public int encrypt3DesCbcNopad(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        return MsoSecuJNI.encrypt3DesCbcNopad(bArr, bArr2, bArr3, bArr4);
    }

    public int setOpenSSLPath(String str) {
        return MsoSecuJNI.setOpenSSLPath(str);
    }

    public int encryptAes128Cbc(byte[] bArr, byte[] bArr2, byte[] bArr3, boolean z, ArrayList<Byte> arrayList) {
        return MsoSecuJNI.encryptAes128Cbc(bArr, bArr2, bArr3, z, arrayList);
    }

    public int decryptAes128Cbc(byte[] bArr, byte[] bArr2, byte[] bArr3, boolean z, ArrayList<Byte> arrayList) {
        return MsoSecuJNI.decryptAes128Cbc(bArr, bArr2, bArr3, z, arrayList);
    }

    public int computeCRC32(byte[] bArr, long j, long j2, boolean z, boolean z2, long j3, long[] jArr) {
        return MsoSecuJNI.computeCRC32(bArr, j, j2, z, z2, j3, jArr);
    }
}
