package morpho.msosecu.sdk.api;

import java.util.ArrayList;

public class MsoSecuJNI {
    public static final native int AES128_BLOCK_LENGTH_get();

    public static final native int DES_BLOCK_LENGTH_get();

    public static final native int MSOSECU_AES128_FINAL_ERR_get();

    public static final native int MSOSECU_AES128_INIT_ERR_get();

    public static final native int MSOSECU_AES128_UPDATE_ERR_get();

    public static final native int MSOSECU_ALLOC_ERR_get();

    public static final native int MSOSECU_BAD_PARAMETER_get();

    public static final native int MSOSECU_BIO_NEW_MEM_ERR_get();

    public static final native int MSOSECU_CRYPTO_PROTOCOLE_ERR_get();

    public static final native int MSOSECU_DELETE_FILE_ERR_get();

    public static final native int MSOSECU_DSA_KEY_ERR_get();

    public static final native int MSOSECU_DSS1_FINAL_ERR_get();

    public static final native int MSOSECU_DSS1_INIT_ERR_get();

    public static final native int MSOSECU_DSS1_UPDATE_ERR_get();

    public static final native int MSOSECU_FILE_EMPTY_get();

    public static final native int MSOSECU_FILE_ERR_get();

    public static final native int MSOSECU_INVALID_CERTIF_get();

    public static final native int MSOSECU_MODE_DSA_ERR_get();

    public static final native int MSOSECU_OK_get();

    public static final native int MSOSECU_OPEN_FILE_ERR_get();

    public static final native int MSOSECU_OPEN_SSL_get();

    public static final native int MSOSECU_PARSE_DATA_TO_X509STRUCT_ERR_get();

    public static final native int MSOSECU_PARSE_DER_TO_X509STRUCT_ERR_get();

    public static final native int MSOSECU_PEM_READ_BIO_ERR_get();

    public static final native int MSOSECU_PUBLIC_KEY_ERR_get();

    public static final native int MSOSECU_READ_FILE_ERR_get();

    public static final native int MSOSECU_RSA_KEY_ERR_get();

    public static final native int MSOSECU_SHA1_FINAL_ERR_get();

    public static final native int MSOSECU_SHA1_INIT_ERR_get();

    public static final native int MSOSECU_SHA1_UPDATE_ERR_get();

    public static final native int MSOSECU_STORE_LOAD_LOCATIONS_ERR_get();

    public static final native int MSOSECU_STORE_NEW_ERR_get();

    public static final native int MSOSECU_WRITE_FILE_ERR_get();

    public static final native int TRIPLE_DES_KEY_LENGTH_get();

    public static final native int computeCRC32(byte[] bArr, long j, long j2, boolean z, boolean z2, long j3, long[] jArr);

    public static final native int decryptAes128Cbc(byte[] bArr, byte[] bArr2, byte[] bArr3, boolean z, ArrayList<Byte> arrayList);

    public static final native int desCrypt(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList);

    public static final native int encrypt3DesCbcNopad(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    public static final native int encryptAes128Cbc(byte[] bArr, byte[] bArr2, byte[] bArr3, boolean z, ArrayList<Byte> arrayList);

    public static final native int genRandom(byte[] bArr);

    public static final native int getHostCertif(ArrayList<Byte> arrayList);

    public static final native int rsaEncrypt(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList);

    public static final native int setOpenSSLPath(String str);

    public static final native int signRSA(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList);

    public static final native int tripleDesCrypt(byte[] bArr, ArrayList<Byte> arrayList);

    public static final native int tripleDesCryptByKey(byte[] bArr, byte[] bArr2, ArrayList<Byte> arrayList);

    public static final native int tripleDesDecrypt(byte[] bArr, ArrayList<Byte> arrayList);

    public static final native int tripleDesSign(byte[] bArr, ArrayList<Byte> arrayList);

    public static final native int tripleDesVerifSign(byte[] bArr, byte[] bArr2, boolean[] zArr);

    public static final native int tunnelingInit1(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, ArrayList<Byte> arrayList);

    public static final native int tunnelingInit2(byte[] bArr);

    public static final native int verifCertif(byte[] bArr, int[] iArr);

    public static final native int verifOfferedSecuritySignature(byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr);

    public static final native int verifSignDSA(byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr);

    public static final native int verifSignRSA(byte[] bArr, byte[] bArr2, byte[] bArr3, int[] iArr);
}
