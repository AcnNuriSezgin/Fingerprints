package nurisezgin.com.fingerprints;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import static nurisezgin.com.fingerprints.FingerprintDialogPlugin.getErrorFingerprintAuth;
import static nurisezgin.com.fingerprints.FingerprintDialogPlugin.getErrorFingerprintEnrollment;
import static nurisezgin.com.fingerprints.FingerprintDialogPlugin.getErrorKeyguardSecurity;
import static nurisezgin.com.fingerprints.FingerprintDialogPlugin.getErrorUnsupportedDevice;

/**
 * Created by nuri on 14.08.2018
 */
public final class FingerprintUtil {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isKeyguardSecure(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager)
                context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.isKeyguardSecure();
    }

    @SuppressWarnings("MissingPermission")
    public static boolean hasEnrolledFingerprints(Context context) {
        FingerprintManagerCompat fm = FingerprintManagerCompat.from(context);
        return fm.hasEnrolledFingerprints();
    }

    @SuppressWarnings("MissingPermission")
    public static boolean isFingerprintAuthAvailable(Context context) {
        FingerprintManagerCompat fm = FingerprintManagerCompat.from(context);
        return fm.isHardwareDetected()
                && fm.hasEnrolledFingerprints();
    }

    public static boolean hasHardwareSupport(Context context) {
        FingerprintManagerCompat fpc = FingerprintManagerCompat.from(context);
        return fpc.isHardwareDetected();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void shouldFingerprintUsable(Context context) throws FingerprintException {
        if (!hasHardwareSupport(context)) {
            throw new FingerprintException(getErrorUnsupportedDevice());
        }

        if (!isKeyguardSecure(context)) {
            throw new FingerprintException(getErrorKeyguardSecurity());
        }

        if (!hasEnrolledFingerprints(context)) {
            throw new FingerprintException(getErrorFingerprintEnrollment());
        }

        if (!isFingerprintAuthAvailable(context)) {
            throw new FingerprintException(getErrorFingerprintAuth());
        }
    }


}
