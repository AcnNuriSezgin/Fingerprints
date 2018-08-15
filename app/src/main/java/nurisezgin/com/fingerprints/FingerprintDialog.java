package nurisezgin.com.fingerprints;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import nurisezgin.com.fingerprints.callbacks.FingerprintCallbacks;
import nurisezgin.com.fingerprints.callbacks.FingerprintDialogCallbacks;
import nurisezgin.com.fingerprints.properties.DialogUIProperties;
import nurisezgin.com.fingerprints.properties.KeystoreProperties;

/**
 * Created by nuri on 14.08.2018
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class FingerprintDialog extends DialogFragment implements FingerprintCallbacks {

    private static final String TAG = "FingerprintDialog";
    private static final int DEFAULT_REQUEST_CODE = 0x7d;
    private static final String KEY_DIALOG_ARGUMENTS = ":dialog_arguments:";
    private static final String KEY_UI_PROPERTIES = ":ui_prop:";
    private static final String KEY_KEYSTORE_PROPERTIES = ":keystore_prop:";
    private FingerprintAdapter mAdapter;
    private FrameLayout contentView;
    private View frame;

    public static FingerprintDialog newInstance(DialogUIProperties uiProperties,
                                                KeystoreProperties keystoreProperties) {

        FingerprintDialog dialog = new FingerprintDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_UI_PROPERTIES, uiProperties);
        bundle.putParcelable(KEY_KEYSTORE_PROPERTIES, keystoreProperties);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static FingerprintDialog newInstance(Fragment fragment,
                                                DialogUIProperties uiProperties,
                                                KeystoreProperties keystoreProperties) {

        FingerprintDialog dialog = new FingerprintDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_UI_PROPERTIES, uiProperties);
        bundle.putParcelable(KEY_KEYSTORE_PROPERTIES, keystoreProperties);
        dialog.setTargetFragment(fragment, DEFAULT_REQUEST_CODE);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static void showDialog(FragmentActivity activity, FingerprintDialog dialog) {
        FragmentTransaction ft = activity.getSupportFragmentManager()
                .beginTransaction();

        dialog.show(ft, TAG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getDialogCallbacks();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FingerprintDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        contentView = new FrameLayout(inflater.getContext());

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        contentView.setMinimumWidth(screenWidth);
        contentView.setMinimumHeight(screenHeight);

        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.get(KEY_DIALOG_ARGUMENTS) != null) {
            Bundle savedArguments = savedInstanceState.getBundle(KEY_DIALOG_ARGUMENTS);

            Parcelable uiProperties = savedArguments.getParcelable(KEY_UI_PROPERTIES);
            Parcelable keyStoreProperties = savedArguments.getParcelable(KEY_KEYSTORE_PROPERTIES);

            getArguments().putParcelable(KEY_UI_PROPERTIES, uiProperties);
            getArguments().putParcelable(KEY_KEYSTORE_PROPERTIES, keyStoreProperties);
        }

        initDialogUI();
    }

    private void initDialogUI() {
        DialogUIProperties uiProperties = getArguments().getParcelable(KEY_UI_PROPERTIES);
        frame = getLayoutInflater().inflate(
                uiProperties.getLayoutId(), contentView, false);
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(frame.getLayoutParams().width, frame.getLayoutParams().height);
        layoutParams.gravity = Gravity.CENTER;
        frame.setLayoutParams(layoutParams);
        contentView.addView(frame);

        if (uiProperties.getCancelButtonId() > 0) {
            frame.findViewById(uiProperties.getCancelButtonId())
                    .setOnClickListener(this::onClickedCancel);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final KeystoreProperties keyProperties = getArguments().getParcelable(KEY_KEYSTORE_PROPERTIES);
        try {
            mAdapter = new FingerprintAdapter(getActivity(), this, keyProperties);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mAdapter.connect();
            }
        } catch (FingerprintException e) {
            e.printStackTrace();

            String message = e.getMessage();
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disconnect();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_DIALOG_ARGUMENTS, getArguments());
    }

    private void onClickedCancel(View v) {
        getDialogCallbacks().onDialogCancelled();
        dismiss();
    }

    private FingerprintDialogCallbacks getDialogCallbacks() {
        Fragment fragment = getTargetFragment();
        if (fragment != null && fragment instanceof FingerprintDialogCallbacks) {
            return (FingerprintDialogCallbacks) fragment;
        }

        Activity activity = getActivity();
        if (activity != null && activity instanceof FingerprintDialogCallbacks) {
            return (FingerprintDialogCallbacks) activity;
        }

        throw new IllegalStateException("Fragment or activity non of them has callback implementation");
    }

    @Override
    public void onAuthenticationSucceeded() {
        getDialogCallbacks().onVerified();
        dismiss();
    }

    @Override
    public void onAuthenticationFailed(String reason) {
        getDialogCallbacks().onError(reason);

        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_wobble);
        frame.clearAnimation();
        frame.startAnimation(anim);
    }
}
