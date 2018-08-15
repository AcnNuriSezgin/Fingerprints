package nurisezgin.com.fingerprints;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import org.junit.Test;
import org.junit.runner.RunWith;

import nurisezgin.com.fingerprints.properties.KeystoreProperties;

import static android.os.Build.VERSION_CODES.M;
import static com.google.common.truth.Truth.assertThat;

/**
 * Created by nuri on 15.08.2018
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = M)
public class CryptoObjectFactoryTest {

    @Test
    public void should_NewCryptoObjectCorrect() throws Exception {
        KeystoreProperties prop = new KeystoreProperties(KeystoreProperties.ANDROID_KEY_STORE, "test");

        FingerprintManagerCompat.CryptoObject object = new CryptoObjectFactory(prop).newCryptoObject();
        assertThat(object).isNotNull();
    }

}