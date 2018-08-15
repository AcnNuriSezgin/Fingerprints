package nurisezgin.com.fingerprints.properties;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nuri on 14.08.2018
 */
public final class KeystoreProperties implements Parcelable {

    public static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private final String keyStoreName;
    private final String keyName;

    public KeystoreProperties(String keyStoreName, String keyName) {
        this.keyStoreName = keyStoreName;
        this.keyName = keyName;
    }

    protected KeystoreProperties(Parcel in) {
        keyStoreName = in.readString();
        keyName = in.readString();
    }

    public static final Creator<KeystoreProperties> CREATOR = new Creator<KeystoreProperties>() {
        @Override
        public KeystoreProperties createFromParcel(Parcel in) {
            return new KeystoreProperties(in);
        }

        @Override
        public KeystoreProperties[] newArray(int size) {
            return new KeystoreProperties[size];
        }
    };

    public String getKeyStoreName() {
        return ANDROID_KEY_STORE;
    }

    public String getKeyName() {
        return keyName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keyStoreName);
        dest.writeString(keyName);
    }
}
