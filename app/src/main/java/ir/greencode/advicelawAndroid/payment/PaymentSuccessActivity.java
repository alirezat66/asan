package ir.greencode.advicelawAndroid.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.utils.BaseActivity;

public class PaymentSuccessActivity  extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
    }
}
