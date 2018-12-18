package ir.greencode.advicelawAndroid.signin;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.retrofit.respObject.AuthenticationRes;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.Constants;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.SoftKeyboard;
import ir.greencode.advicelawAndroid.utils.Utility;

public class SignInActivity extends BaseActivity implements SignInInterface {
    SignInPresenter presenter;
    @BindView(R.id.edtPhone)
    TextInputEditText edtPhone;
    @BindView(R.id.btnSendCode)
    Button btnSendCode;
    @BindView(R.id.root)
    ConstraintLayout root;

    KProgressHUD progress;
    @BindView(R.id.img_onboarding)
    ImageView imgOnboarding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        presenter = new SignInPresenter(this);

        SoftKeyboard softKeyboard;
        InputMethodManager im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        /// this is for hide and show keyboard
        softKeyboard = new SoftKeyboard(root, im);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged()
        {

            @Override
            public void onSoftKeyboardHide()
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgOnboarding.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onSoftKeyboardShow()
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imgOnboarding.setVisibility(View.GONE);
                    }
                });
            }
        });

        /// finish keyboard listener
    }



    @OnClick(R.id.btnSendCode)
    public void onClick() {
        progress = Utility.makeWaiting(Constants.WaitingTitle, Constants.WaitingDesc, this);
        progress.show();
        presenter.checkValidationPhone(edtPhone.getText().toString());
    }



    @Override
    public void onPhoneInvalid() {
        if (progress.isShowing())
            progress.dismiss();
        Snackbar
                .make(root, "شماره تماس صحیح نیست.",
                        Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onAuthenticate(AuthenticationRes res) {
        if (progress.isShowing())
            progress.dismiss();
        PreferencesData.saveString(Constants.PREF_TOKEN, res.getResult().getToken(), this);
        PreferencesData.saveString(Constants.PREF_USER_NAME, res.getResult().getUserName(), this);
        PreferencesData.saveString(Constants.PREF_USER_IMAGE, res.getResult().getUserImage(), this);
        PreferencesData.saveString(Constants.PREF_USER_FAMILY, res.getResult().getUserFamily(), this);
        PreferencesData.saveInt(Constants.Pref_USER_ID,res.getResult().getId(),this);

        Intent intent = new Intent(this, VerificationActivity.class);
        intent.putExtra("code", res.getResult().getCode());
        intent.putExtra("phone", edtPhone.getText().toString());
        startActivity(intent);
        finish();

    }

    @Override
    public void onAuthenticateFaild(String resp) {
        if (progress.isShowing())
            progress.dismiss();
                Snackbar
                .make(root, resp,
                        Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onGCMOK() {

    }
}
