package com.morpho.morphosmart.sdk;

import java.util.ArrayList;

public interface IMsoSecu {
    int computeCRC32(byte[] bArr, long j, long j2, boolean z, boolean z2, long j3, long[] jArr);

    int decryptAes128Cbc(byte[] bArr, byte[] bArr2, byte[] bArr3, boolean z, ArrayList<Byte> arrayList);

    int desCrypt(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList);

    int encrypt3DesCbcNopad(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    int encryptAes128Cbc(byte[] bArr, byte[] bArr2, byte[] bArr3, boolean z, ArrayList<Byte> arrayList);

    int genRandom(byte[] bArr);

    int getHostCertif(ArrayList<Byte> arrayList);

    int rsaEncrypt(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList);

    int setOpenSSLPath(String str);

    int signRSA(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList);

    int tripleDesCrypt(byte[] bArr, ArrayList<Byte> arrayList);

    int tripleDesCryptByKey(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList);

    int tripleDesDecrypt(byte[] bArr, ArrayList<Byte> arrayList);

    int tripleDesSign(byte[] bArr, ArrayList<Byte> arrayList);

    int tripleDesVerifSign(byte[] bArr, byte[] bArr2, boolean[] zArr);

    int tunnelingInit1(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, ArrayList<Byte> arrayList);

    int tunnelingInit2(byte[] bArr);

    int verifCertif(byte[] bArr, int[] iArr);

    int verifOfferedSecuritySignature(byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr);

    int verifSignDSA(byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr);

    int verifSignRSA(byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr);
}
