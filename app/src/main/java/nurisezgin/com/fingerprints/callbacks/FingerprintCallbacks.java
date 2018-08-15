package nurisezgin.com.fingerprints.callbacks;

/**
 * Created by nuri on 14.08.2018
 */
public interface FingerprintCallbacks {

    void onAuthenticationSucceeded();

    void onAuthenticationFailed(String reason);

}
