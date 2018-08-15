package nurisezgin.com.fingerprints;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import nurisezgin.com.fingerprints.callbacks.FingerprintCallbacks;
import nurisezgin.com.fingerprints.properties.KeystoreProperties;

/**
 * Created by nuri on 14.08.2018
 */
final class FingerprintAdapter extends FingerprintManagerCompat.AuthenticationCallback {

    private boolean mSelfChanged;
    private CancellationSignal mCancellationSignal;
    private FingerprintManagerCompat mFingerprintManager;
    private KeystoreProperties mKeyConfig;
    private FingerprintCallbacks callback;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    FingerprintAdapter(Context context, FingerprintCallbacks callback, KeystoreProperties keyConfig) throws FingerprintException {
        this.callback = callback;
        this.mKeyConfig = keyConfig;

        FingerprintUtil.shouldFingerprintUsable(context);

        this.mFingerprintManager = FingerprintManagerCompat.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void connect() throws Exception {
        FingerprintManagerCompat.CryptoObject cryptoObject = new CryptoObjectFactory(mKeyConfig)
                .newCryptoObject();
        mCancellationSignal = new CancellationSignal();
        mSelfChanged = false;
        mFingerprintManager.authenticate(
                cryptoObject, 0, mCancellationSignal, this, null);
    }

    void disconnect() {
        if (mCancellationSignal != null) {
            mSelfChanged = true;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        if (!mSelfChanged) {
            callback.onAuthenticationFailed(errString.toString());
        }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        callback.onAuthenticationFailed(helpString.toString());
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        callback.onAuthenticationSucceeded();
    }

    @Override
    public void onAuthenticationFailed() {
        callback.onAuthenticationFailed(FingerprintDialogPlugin.getErrorFingerprintUnrecognized());
    }
}
