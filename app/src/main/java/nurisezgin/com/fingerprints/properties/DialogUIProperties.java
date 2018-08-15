package nurisezgin.com.fingerprints.properties;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

/**
 * Created by nuri on 15.08.2018
 */
public final class DialogUIProperties implements Parcelable {

    @LayoutRes private final int layoutId;
    @IdRes private final int cancelButtonId;

    public DialogUIProperties(int layoutId, int cancelButtonId) {
        this.layoutId = layoutId;
        this.cancelButtonId = cancelButtonId;
    }

    protected DialogUIProperties(Parcel in) {
        layoutId = in.readInt();
        cancelButtonId = in.readInt();
    }

    public static final Creator<DialogUIProperties> CREATOR = new Creator<DialogUIProperties>() {
        @Override
        public DialogUIProperties createFromParcel(Parcel in) {
            return new DialogUIProperties(in);
        }

        @Override
        public DialogUIProperties[] newArray(int size) {
            return new DialogUIProperties[size];
        }
    };

    public int getLayoutId() {
        return layoutId;
    }

    public int getCancelButtonId() {
        return cancelButtonId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(layoutId);
        dest.writeInt(cancelButtonId);
    }
}
