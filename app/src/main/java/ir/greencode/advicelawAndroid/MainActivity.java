package ir.greencode.advicelawAndroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.cats.CategoryActivity;
import ir.greencode.advicelawAndroid.main.ActivityMain;
import ir.greencode.advicelawAndroid.payment.PaymentSuccessActivity;
import ir.greencode.advicelawAndroid.signin.SignInActivity;
import ir.greencode.advicelawAndroid.signin.VerificationActivity;
import ir.greencode.advicelawAndroid.vote.VoteActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.btn_verification)
    Button btnVerification;
    @BindView(R.id.btnMenu)
    Button btnMenu;
    @BindView(R.id.btnCat)
    Button btnCat;
    @BindView(R.id.btnVote)
    Button btnVote;
    @BindView(R.id.btnPayment)
    Button btnPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public void signIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        start(intent);
    }

    public void verification() {
        Intent intent = new Intent(this, VerificationActivity.class);
        start(intent);
    }

    private void catPage() {
        Intent intent = new Intent(this, CategoryActivity.class);
        start(intent);
    }

    public void start(Intent intent) {
        startActivity(intent);
    }

    @OnClick({R.id.btn_sign_in, R.id.btn_verification, R.id.btnMenu, R.id.btnCat, R.id.btnVote,R.id.btnPayment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                signIn();
                break;
            case R.id.btn_verification:
                verification();
                break;
            case R.id.btnMenu:
                mainPage();
                break;
            case R.id.btnCat:
                catPage();
                break;
            case R.id.btnVote:
                votePage();
                break;
            case R.id.btnPayment:
                paymentPag();
                break;

        }
    }

    private void paymentPag() {
        Intent intent = new Intent(this, PaymentSuccessActivity.class);
        start(intent);
    }

    private void votePage() {
        Intent intent = new Intent(this, VoteActivity.class);
        start(intent);
    }


    private void mainPage() {
        Intent intent = new Intent(this, ActivityMain.class);
        start(intent);
    }


}
