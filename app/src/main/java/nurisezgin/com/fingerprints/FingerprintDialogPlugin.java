package nurisezgin.com.fingerprints;

/**
 * Created by nuri on 14.08.2018
 */
public final class FingerprintDialogPlugin {

    private static String errorKeyguardSecurity;
    private static String errorFingerprintEnrollment;
    private static String errorFingerprintAuth;
    private static String errorFingerprintUnrecognized;
    private static String errorUnsupportedDevice;

    public static void setErrorKeyguardSecurity(String message) {
        errorKeyguardSecurity = message;
    }

    public static void setErrorFingerprintEnrollment(String message) {
        errorFingerprintEnrollment = message;
    }

    public static void setErrorFingerprintAuth(String message) {
        errorFingerprintAuth = message;
    }

    public static void setErrorFingerprintUnrecognized(String message) {
        errorFingerprintUnrecognized = message;
    }

    public static void setErrorUnsupportedDevice(String message) {
        errorUnsupportedDevice = message;
    }

    public static String getErrorKeyguardSecurity() {
        return errorKeyguardSecurity;
    }

    public static String getErrorFingerprintEnrollment() {
        return errorFingerprintEnrollment;
    }

    public static String getErrorFingerprintAuth() {
        return errorFingerprintAuth;
    }

    public static String getErrorFingerprintUnrecognized() {
        return errorFingerprintUnrecognized;
    }

    public static String getErrorUnsupportedDevice() {
        return errorUnsupportedDevice;
    }
}
