package ir.greencode.advicelawAndroid.paymentreturn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.main.ActivityMain;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.PreferencesData;

public class RecieverActivity extends BaseActivity {

    @BindView(R.id.state_txt)
    TextView stateTxt;
    @BindView(R.id.message_txt)
    TextView messageTxt;
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        ButterKnife.bind(this);


        String date = PreferencesData.getString("call_time", this);

        Intent in = getIntent();
        Uri data = in.getData();
// دو سطر بالا دیتاهای ارسال شده به اکتیویتی را دریافت مینماید و به data کپی میکند.
        if (data != null) {

// در سطر زیر عبارت "varchar://" را از داده های دریافتی حذف مینماییم این عبارت همان بخش scheme موجود در منیفست می باشد.
            String rdata = data.toString().replace("varchar://", "");

// حال rdata شامل داده هاییست که بعد intent:// در قسمت آموزش سمت وب فرستاده شده است می باشد.
//برای این آموزش فقط عدد ارسال کرده ایم. و حالت های مختلف را با توست هندل میکنیم.
            if (rdata.equals("1")) {
                String text = "مشاور شما فردا، ساعت " +" "+date+" "+ " با شما تماس خواهد گرفت لطفا حتما در این ساعت آنلاین باشید و سعی کنید به اینترنت با کیفیت دسترسی داشته باشید";

                messageTxt.setText(text);
                stateTxt.setText("پرداخت شما با موفقیت انجام شد.");
            } else {
                stateTxt.setText("پرداخت شما با خطا متوجه شد.");
                stateTxt.setText("لطفا در صورت تمایل مجددا اقدام نمایید.");
            }
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecieverActivity.this, ActivityMain.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
    }


}
