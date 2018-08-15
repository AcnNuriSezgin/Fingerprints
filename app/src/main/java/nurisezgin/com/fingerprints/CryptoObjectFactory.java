package nurisezgin.com.fingerprints;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import nurisezgin.com.fingerprints.properties.KeystoreProperties;

/**
 * Created by nuri on 14.08.2018
 */
final class CryptoObjectFactory {

    private final KeystoreProperties keyConfig;
    private KeyStore mKeyStore;

    public CryptoObjectFactory(KeystoreProperties config) {
        keyConfig = config;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    FingerprintManagerCompat.CryptoObject newCryptoObject() throws Exception {
        mKeyStore = KeyStore.getInstance(keyConfig.getKeyStoreName());
        KeyGenerator mKeyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES, keyConfig.getKeyStoreName());

        mKeyStore.load(null);
        KeyGenParameterSpec spec = getKeyGenParameterSpec(keyConfig.getKeyName());
        mKeyGenerator.init(spec);
        mKeyGenerator.generateKey();
        Cipher cipher = createCipher();
        initCipher(cipher, keyConfig.getKeyName());
        return new FingerprintManagerCompat.CryptoObject(cipher);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    private KeyGenParameterSpec getKeyGenParameterSpec(String keyName) {
        KeyGenParameterSpec.Builder builder =
                new KeyGenParameterSpec.Builder(keyName,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT);

        builder.setBlockModes(KeyProperties.BLOCK_MODE_CBC);
        builder.setUserAuthenticationRequired(true);
        builder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setInvalidatedByBiometricEnrollment(false);
        }
        return builder.build();
    }

    private void initCipher(Cipher cipher, String key) throws Exception {
        mKeyStore.load(null);
        SecretKey secretKey = (SecretKey) mKeyStore.getKey(key, null);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    }

    private Cipher createCipher() throws Exception {
        return Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/"
                + KeyProperties.ENCRYPTION_PADDING_PKCS7);
    }

}
