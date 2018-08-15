package nurisezgin.com.fingerprints.callbacks;

/**
 * Created by nuri on 14.08.2018
 */
public interface FingerprintDialogCallbacks {

    void onVerified();

    void onError(String reason);

    void onDialogCancelled();

}
