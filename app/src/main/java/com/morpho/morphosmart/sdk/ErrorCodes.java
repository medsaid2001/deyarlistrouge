package com.morpho.morphosmart.sdk;

public class ErrorCodes {
    public static final int CLASS_NOT_INSTANTIATED = -98;
    public static final int MORPHOERR_ADVANCED_SECURITY_LEVEL_MISMATCH = -65;
    public static final int MORPHOERR_ADVANCED_SECURITY_LEVEL_NOT_AVAILABLE = -71;
    public static final int MORPHOERR_ALREADY_ENROLLED = -12;
    public static final int MORPHOERR_BADPARAMETER = -5;
    public static final int MORPHOERR_BAD_COMPRESSION = -38;
    public static final int MORPHOERR_BAD_FINAL_FINGER_PRINT_QUALITY = -66;
    public static final int MORPHOERR_BASE_ALREADY_EXISTS = -14;
    public static final int MORPHOERR_BASE_NOT_FOUND = -13;
    public static final int MORPHOERR_CANT_GRAN_PERMISSION_USB = -99;
    public static final int MORPHOERR_CAPTURE_FAILED = -78;
    public static final int MORPHOERR_CERTIF_INVALID = -43;
    public static final int MORPHOERR_CERTIF_UNKNOW = -40;
    public static final int MORPHOERR_CLOSE_COM = -4;
    public static final int MORPHOERR_CMDE_ABORTED = -26;
    public static final int MORPHOERR_COM_NOT_OPEN = -34;
    public static final int MORPHOERR_CONNECT = -3;
    public static final int MORPHOERR_CORRUPTED_CLASS = -22;
    public static final int MORPHOERR_DB_EMPTY = -11;
    public static final int MORPHOERR_DB_FULL = -10;
    public static final int MORPHOERR_DEVICE_LOCKED = -57;
    public static final int MORPHOERR_DEVICE_NOT_LOCK = -58;
    public static final int MORPHOERR_ELT_ALREADY_PRESENT = -35;
    public static final int MORPHOERR_FFD = -46;
    public static final int MORPHOERR_FFD_FINGER_MISPLACED = -68;
    public static final int MORPHOERR_FIELD_INVALID = -32;
    public static final int MORPHOERR_FIELD_NOT_FOUND = -21;
    public static final int MORPHOERR_FILE_ALREADY_EXISTS = -76;
    public static final int MORPHOERR_FVP_FINGER_MISPLACED_OR_WITHDRAWN = -63;
    public static final int MORPHOERR_FVP_MINUTIAE_SECURITY_MISMATCH = -62;
    public static final int MORPHOERR_INTEGER_INITIALIZATION = -94;
    public static final int MORPHOERR_INTERNAL = -1;
    public static final int MORPHOERR_INVALID_CLASS = -41;
    public static final int MORPHOERR_INVALID_FINGER = -75;
    public static final int MORPHOERR_INVALID_PK_FORMAT = -27;
    public static final int MORPHOERR_INVALID_TEMPLATE = -17;
    public static final int MORPHOERR_INVALID_USER_DATA = -31;
    public static final int MORPHOERR_INVALID_USER_ID = -30;
    public static final int MORPHOERR_KEY_NOT_FOUND = -69;
    public static final int MORPHOERR_LICENSE_MISSING = -64;
    public static final int MORPHOERR_LONG_INITIALIZATION = -93;
    public static final int MORPHOERR_MEMORY_DEVICE = -7;
    public static final int MORPHOERR_MEMORY_PC = -6;
    public static final int MORPHOERR_MIXED_TEMPLATE = -25;
    public static final int MORPHOERR_MOIST_FINGER = -47;
    public static final int MORPHOERR_MOVED_FINGER = -73;
    public static final int MORPHOERR_NOCALLTO_DBQUERRYFIRST = -36;
    public static final int MORPHOERR_NOT_IMPLEMENTED = -18;
    public static final int MORPHOERR_NO_ASSOCIATED_DB = -15;
    public static final int MORPHOERR_NO_ASSOCIATED_DEVICE = -16;
    public static final int MORPHOERR_NO_HIT = -8;
    public static final int MORPHOERR_NO_MORE_OTP = -54;
    public static final int MORPHOERR_NO_REGISTERED_TEMPLATE = -20;
    public static final int MORPHOERR_NO_SERVER = -48;
    public static final int MORPHOERR_OTP_ENROLL_FAILED = -52;
    public static final int MORPHOERR_OTP_ENROLL_NEEDED = -56;
    public static final int MORPHOERR_OTP_IDENT_FAILED = -53;
    public static final int MORPHOERR_OTP_LOCK_ENROLL = -61;
    public static final int MORPHOERR_OTP_LOCK_GEN_OTP = -59;
    public static final int MORPHOERR_OTP_LOCK_SET_PARAM = -60;
    public static final int MORPHOERR_OTP_NOT_INITIALIZED = -49;
    public static final int MORPHOERR_OTP_NO_HIT = -55;
    public static final int MORPHOERR_OTP_PIN_NEEDED = -50;
    public static final int MORPHOERR_OTP_REENROLL_NOT_ALLOWED = -51;
    public static final int MORPHOERR_OUT_OF_FIELD = -29;
    public static final int MORPHOERR_PROTOCOLE = -2;
    public static final int MORPHOERR_RESUME_CONNEXION = -95;
    public static final int MORPHOERR_RESUME_CONNEXION_ALREADY_STARTED = -96;
    public static final int MORPHOERR_SAME_FINGER = -28;
    public static final int MORPHOERR_SATURATED_FINGER = -74;
    public static final int MORPHOERR_SECU = -39;
    public static final int MORPHOERR_SIGNER_ID = -44;
    public static final int MORPHOERR_SIGNER_ID_INVALID = -45;
    public static final int MORPHOERR_STATUS = -9;
    public static final int MORPHOERR_SVC_LOST_DEVICE = -110;
    public static final int MORPHOERR_TIMEOUT = -19;
    public static final int MORPHOERR_TO_MANY_FIELD = -24;
    public static final int MORPHOERR_TO_MANY_TEMPLATE = -23;
    public static final int MORPHOERR_UNAVAILABLE = -72;
    public static final int MORPHOERR_USB_DEVICE_NAME_UNKNOWN = -42;
    public static final int MORPHOERR_USB_PERMISSION_DENIED = -97;
    public static final int MORPHOERR_USER = -37;
    public static final int MORPHOERR_USER_NOT_FOUND = -33;
    public static final int MORPHOWARNING_WSQ_COMPRESSION_RATIO = -70;
    public static final int MORPHO_OK = 0;

    private ErrorCodes() {
    }

    public static String getError(int i, int i2) {
        if (i == -110) {
            return "A connected device to the Android service has been lost, this can occur when the Android OS goes to sleep or device detached.";
        }
        if (i == -99) {
            return "Could not grant permissions to USB";
        }
        switch (i) {
            case MORPHOERR_USB_PERMISSION_DENIED /*-97*/:
                return "USB permission denied.";
            case MORPHOERR_RESUME_CONNEXION_ALREADY_STARTED /*-96*/:
                return "Resume Connexion Already Started.";
            case MORPHOERR_RESUME_CONNEXION /*-95*/:
                return "Cannot connect biometrics device. USB Permission denied.";
            case MORPHOERR_INTEGER_INITIALIZATION /*-94*/:
                return "Integer initialization error, you must initialize the Integer object with new Integer(0).";
            case MORPHOERR_LONG_INITIALIZATION /*-93*/:
                return "Long initialization error, you must initialize the Long object with new Long(0).";
            default:
                switch (i) {
                    case MORPHOERR_FILE_ALREADY_EXISTS /*-76*/:
                        return "User area data is not empty";
                    case MORPHOERR_INVALID_FINGER /*-75*/:
                        return "The finger is invalid.";
                    case MORPHOERR_SATURATED_FINGER /*-74*/:
                        return "The finger can be too shiny or too much external light.";
                    case MORPHOERR_MOVED_FINGER /*-73*/:
                        return "The finger moved during acquisition or removed earlier.";
                    default:
                        switch (i) {
                            case MORPHOERR_LICENSE_MISSING /*-64*/:
                                return "A required license is missing.";
                            case MORPHOERR_FVP_FINGER_MISPLACED_OR_WITHDRAWN /*-63*/:
                                return "Misplaced or withdrawn finger has been detected during acquisition (MorphoSmart FINGER VP only).";
                            case MORPHOERR_FVP_MINUTIAE_SECURITY_MISMATCH /*-62*/:
                                return "Security level mismatch: attempt to match fingerprint template in high security level (MorphoSmart FINGER VP only).";
                            case MORPHOERR_OTP_LOCK_ENROLL /*-61*/:
                                return "ILV_OTP_ENROLL_USER Locked.";
                            case MORPHOERR_OTP_LOCK_SET_PARAM /*-60*/:
                                return "ILV_OTP_SET_PARAMETERS  Locked.";
                            case MORPHOERR_OTP_LOCK_GEN_OTP /*-59*/:
                                return "ILV_OTP_GENERATE Locked.";
                            case MORPHOERR_DEVICE_NOT_LOCK /*-58*/:
                                return "The device is not locked.";
                            case MORPHOERR_DEVICE_LOCKED /*-57*/:
                                return "The device is locked.";
                            case MORPHOERR_OTP_ENROLL_NEEDED /*-56*/:
                                return "Enrollment needed before generating OTP.";
                            case MORPHOERR_OTP_NO_HIT /*-55*/:
                                return "Authentication or Identification failed.";
                            case MORPHOERR_NO_MORE_OTP /*-54*/:
                                return "No more OTP available (sequence number = 0).";
                            case MORPHOERR_OTP_IDENT_FAILED /*-53*/:
                                return "Identification failed.";
                            case MORPHOERR_OTP_ENROLL_FAILED /*-52*/:
                                return "Enrollment failed.";
                            case MORPHOERR_OTP_REENROLL_NOT_ALLOWED /*-51*/:
                                return "User is not allowed to be reenrolled.";
                            case MORPHOERR_OTP_PIN_NEEDED /*-50*/:
                                return "Code pin is needed : it is the first enrollment.";
                            case MORPHOERR_OTP_NOT_INITIALIZED /*-49*/:
                                return "No parameter has been initialized.";
                            case MORPHOERR_NO_SERVER /*-48*/:
                                return "The Morpho MorphoSmart Service Provider Usb Server is stopped or not installed.";
                            case MORPHOERR_MOIST_FINGER /*-47*/:
                                return "The finger can be too moist or the scanner is wet.";
                            case MORPHOERR_FFD /*-46*/:
                                return "False Finger Detected.";
                            case MORPHOERR_SIGNER_ID_INVALID /*-45*/:
                                return "The X984 certificate identity size is different to 20 octets (SHA_1 size).";
                            case MORPHOERR_SIGNER_ID /*-44*/:
                                return "The certificate identity is not the same than the X984 certificate identity.";
                            case MORPHOERR_CERTIF_INVALID /*-43*/:
                                return "The certificate is not valid.";
                            case MORPHOERR_USB_DEVICE_NAME_UNKNOWN /*-42*/:
                                return "The specified Usb device is not plugged.";
                            case MORPHOERR_INVALID_CLASS /*-41*/:
                                return "The class has been destroyed.";
                            case MORPHOERR_CERTIF_UNKNOW /*-40*/:
                                return "The MSO has not the certificate necessary to verify the signature.";
                            case MORPHOERR_SECU /*-39*/:
                                return "Security error.";
                            case MORPHOERR_BAD_COMPRESSION /*-38*/:
                                return "The Compression is not valid.";
                            case MORPHOERR_USER /*-37*/:
                                return "The communication callback functions returns error between -10000 and -10499.";
                            case MORPHOERR_NOCALLTO_DBQUERRYFIRST /*-36*/:
                                return "You have to call C_MORPHO_Database::DbQueryFirst to initialize the querry.";
                            case MORPHOERR_ELT_ALREADY_PRESENT /*-35*/:
                                return "This element is already present in the list.";
                            case MORPHOERR_COM_NOT_OPEN /*-34*/:
                                return "Serial COM has not been opened.";
                            case MORPHOERR_USER_NOT_FOUND /*-33*/:
                                return "User is not found.";
                            case MORPHOERR_FIELD_INVALID /*-32*/:
                                return "Additional field name length is more than MORPHO_FIELD_NAME_LEN.";
                            case MORPHOERR_INVALID_USER_DATA /*-31*/:
                                return "The user data are not valid.";
                            case MORPHOERR_INVALID_USER_ID /*-30*/:
                                return "UserID is not valid.";
                            case MORPHOERR_OUT_OF_FIELD /*-29*/:
                                return "The number of the additional field is more than 128.";
                            case MORPHOERR_SAME_FINGER /*-28*/:
                                return "User gave twice the same finger.";
                            case MORPHOERR_INVALID_PK_FORMAT /*-27*/:
                                return "Invalid PK format.";
                            case MORPHOERR_CMDE_ABORTED /*-26*/:
                                return "Command has been aborted.";
                            case MORPHOERR_MIXED_TEMPLATE /*-25*/:
                                return "Templates with differents formats are mixed.";
                            case MORPHOERR_TO_MANY_FIELD /*-24*/:
                                return "There are too many fields.";
                            case MORPHOERR_TO_MANY_TEMPLATE /*-23*/:
                                return "There are too many templates.";
                            case MORPHOERR_CORRUPTED_CLASS /*-22*/:
                                return "Class has been corrupted.";
                            case MORPHOERR_FIELD_NOT_FOUND /*-21*/:
                                return "Field does not exist.";
                            case MORPHOERR_NO_REGISTERED_TEMPLATE /*-20*/:
                                return "No templates have been registered.";
                            case MORPHOERR_TIMEOUT /*-19*/:
                                return "No response after defined time.";
                            case MORPHOERR_NOT_IMPLEMENTED /*-18*/:
                                return "Command not yet implemented in this release.";
                            case MORPHOERR_INVALID_TEMPLATE /*-17*/:
                                return "The template is not valid.";
                            case MORPHOERR_NO_ASSOCIATED_DEVICE /*-16*/:
                                return "Database object has been instanciated without C_MORPHO_Device::GetDatabase.";
                            case MORPHOERR_NO_ASSOCIATED_DB /*-15*/:
                                return "User object has been instanciated without C_MORPHO_Database::GetUser.";
                            case MORPHOERR_BASE_ALREADY_EXISTS /*-14*/:
                                return "The specified base already exist.";
                            case MORPHOERR_BASE_NOT_FOUND /*-13*/:
                                return "The specified base does not exist.";
                            case MORPHOERR_ALREADY_ENROLLED /*-12*/:
                                return "User has already been enrolled.";
                            case MORPHOERR_DB_EMPTY /*-11*/:
                                return "The database is empty.";
                            case MORPHOERR_DB_FULL /*-10*/:
                                return "The database is full.";
                            case MORPHOERR_STATUS /*-9*/:
                                return "MSO returned an unknown status error.";
                            case -8:
                                return "Authentication or Identification failed.";
                            case -7:
                                return "Not enough memory for the creation of a database in the MSO.";
                            case -6:
                                return "Not enough memory (in the PC).";
                            case -5:
                                return "Invalid parameter.";
                            case -4:
                                return "Error while closing communication port.";
                            case -3:
                                return "Can not connect biometrics device.";
                            case -2:
                                return "Communication protocole error.";
                            case -1:
                                return "Biometrics device performed an internal error.";
                            case 0:
                                return "No error.";
                            default:
                                return String.format("Unknown error %d, Internal Error = %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
                        }
                }
        }
    }

    public static Boolean IntegrerInitializationValueOf(CustomInteger customInteger) {
        return false;
    }

    public static Boolean LongInitializationValueOf(CustomLong customLong) {
        return false;
    }
}
